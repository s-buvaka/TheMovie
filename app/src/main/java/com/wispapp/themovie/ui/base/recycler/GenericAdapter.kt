package com.wispapp.themovie.ui.base.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.swipeable.SwipeableItemConstants
import com.h6ah4i.android.widget.advrecyclerview.utils.RecyclerViewAdapterUtils.getViewHolder

abstract class GenericAdapter<T>(listener: OnItemClickListener<T>? = null) :
    RecyclerView.Adapter<BaseViewHolder<*>>(),
    SwipeableItemAdapter<BaseViewHolder<T>>, RecyclerViewSwipeManager.OnItemSwipeEventListener {

    private var itemList = mutableListOf<DataWrapper<T>>()
    private var itemClickListener: OnItemClickListener<T>? = listener
    private var diffUtil: GenericItemDiff<T>? = null

    init {
        @Suppress("LeakingThis")
        setHasStableIds(true)
    }

    abstract override fun getItemId(position: Int): Long

    protected abstract fun getLayoutId(position: Int, obj: T): Int

    /**
     * Override method [getViewHolder] if you want using more than one View Holder in adapter
     */
    protected open fun getViewHolder(view: View, viewType: Int): BaseViewHolder<*> =
        ViewHolderFactory.create(view, viewType)

    internal fun getItem(position: Int) = itemList[position]

    fun update(items: List<T>) =
        if (diffUtil != null) updateWithDiffUtils(items)
        else notifyUpdate(items)

    fun setDiffUtil(diffUtilImpl: GenericItemDiff<T>) {
        diffUtil = diffUtilImpl
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> =
        getViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            , viewType
        )


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) =
        @Suppress("UNCHECKED_CAST")
        (holder as Binder<T>).bind(itemList[position], itemClickListener)

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int =
        getLayoutId(position, itemList[position].item)

    override fun onItemSwipeStarted(position: Int) {
        closeSwipe()
        itemList[position].isPinned = true
    }

    override fun onItemSwipeFinished(position: Int, result: Int, afterSwipeReaction: Int) {}

    override fun onSwipeItemStarted(holder: BaseViewHolder<T>, position: Int) {}

    override fun onSetSwipeBackground(holder: BaseViewHolder<T>, position: Int, type: Int) {}

    override fun onGetSwipeReactionType(holder: BaseViewHolder<T>, position: Int, x: Int, y: Int) =
        if (hitTest(holder.swipeableContainerView, x, y)) {
            SwipeableItemConstants.REACTION_CAN_SWIPE_BOTH_H
        } else {
            SwipeableItemConstants.REACTION_CAN_NOT_SWIPE_BOTH_H
        }

    override fun onSwipeItem(holder: BaseViewHolder<T>, position: Int, result: Int) =
        when (result) {
            SwipeableItemConstants.RESULT_SWIPED_LEFT -> ResultAction(
                ResultAction.RESULT.SWIPE_LEFT,
                this,
                position
            )
            else -> unpinResultAction(position)
        }

    private fun updateWithDiffUtils(items: List<T>) {
        val result = DiffUtil.calculateDiff(
            GenericDiffUtil(
                itemList.map { it.item },
                items,
                diffUtil!!
            )
        )

        itemList.clear()
        itemList.addAll(items.map { DataWrapper(it) })
        result.dispatchUpdatesTo(this)
    }

    private fun notifyUpdate(items: List<T>) {
        itemList = items.map { DataWrapper(it) }.toMutableList()
        notifyDataSetChanged()
    }

    private fun closeSwipe() {
        for (position in 0 until itemCount) {
            val data = itemList[position]
            if (data.isPinned) {
                data.isPinned = false
            }
        }
    }

    private fun unpinResultAction(position: Int) =
        if (position != RecyclerView.NO_POSITION) {
            ResultAction(ResultAction.RESULT.UNPIN, this, position)
        } else {
            null
        }


    private fun hitTest(v: View, x: Int, y: Int): Boolean {
        val tx = (v.translationX + 0.5f).toInt()
        val ty = (v.translationY + 0.5f).toInt()
        val left = v.left + tx
        val right = v.right + tx
        val top = v.top + ty
        val bottom = v.bottom + ty

        return x in left..right && y >= top && y <= bottom
    }

    class DataWrapper<T>(var item: T) {
        var isPinned: Boolean = false
    }

    internal interface Binder<T> {

        fun bind(data: DataWrapper<T>, listener: OnItemClickListener<T>?)
    }

    interface OnItemClickListener<T> {

        fun onClickItem(data: T) {}

        /**
         * Implements this methods when you need process clicks on swipeable buttons
         */
        fun onClickRightButton(data: T) {}

        fun onClickMiddleButton(data: T) {}
        fun onClickLeftButton(data: T) {}
    }

    interface GenericItemDiff<T> {

        fun isSame(
            oldItems: List<T>,
            newItems: List<T>,
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean

        fun isSameContent(
            oldItems: List<T>,
            newItems: List<T>,
            oldItemPosition: Int,
            newItemPosition: Int
        ): Boolean
    }
}