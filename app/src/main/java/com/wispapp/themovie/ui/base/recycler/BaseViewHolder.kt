package com.wispapp.themovie.ui.base.recycler

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import androidx.annotation.DimenRes
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractSwipeableItemViewHolder

abstract class BaseViewHolder<T>(itemView: View) : AbstractSwipeableItemViewHolder(itemView),
    GenericAdapter.Binder<T> {

    private var dimenRes: Int = 0

    /**
     * Override [getSwipeableContainerView] method if you want set swipeable view holder
     */
    override fun getSwipeableContainerView(): View =
        View(itemView.context)

    override fun bind(
        data: GenericAdapter.DataWrapper<T>,
        listener: GenericAdapter.OnItemClickListener<T>?
    ) {
        setupSwipeSetting(data)
    }

    /**
     * Invoke method [setSwipeSize] for manual set swipe dimension
     */
    protected open fun setSwipeSize(@DimenRes dimen: Int) {
        dimenRes = dimen
    }

    private fun setupSwipeSetting(data: GenericAdapter.DataWrapper<T>?) {
        maxLeftSwipeAmount = swapDimension()
        maxRightSwipeAmount = 0F
        swipeItemHorizontalSlideAmount = if (data?.isPinned == true) swapDimension() else 0F
    }

    private fun swapDimension(): Float =
        if (dimenRes == 0)
            -DEFAULT_SWIPE_SIZE / getScreenWidth(itemView.context)
        else
            -itemView.context.resources.getDimensionPixelSize(dimenRes).toFloat() / getScreenWidth(
                itemView.context
            )

    private fun getScreenWidth(context: Context): Int {
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    companion object {

        private const val DEFAULT_SWIPE_SIZE = 270F

    }
}