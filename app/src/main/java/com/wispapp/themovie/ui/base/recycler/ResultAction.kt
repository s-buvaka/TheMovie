package com.wispapp.themovie.ui.base.recycler

import com.h6ah4i.android.widget.advrecyclerview.swipeable.RecyclerViewSwipeManager
import com.h6ah4i.android.widget.advrecyclerview.swipeable.action.SwipeResultAction

class ResultAction<T>(
    private val resultAction: RESULT,
    private var adapter: GenericAdapter<T>,
    private val position: Int
) : SwipeResultAction(
    if (resultAction == RESULT.UNPIN) RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_DEFAULT
    else RecyclerViewSwipeManager.AFTER_SWIPE_REACTION_MOVE_TO_SWIPED_DIRECTION
) {

    override fun onPerformAction() {
        super.onPerformAction()
        val data = adapter.getItem(position)

        when (resultAction) {
            RESULT.SWIPE_LEFT -> if (!data.isPinned) {
                data.isPinned = true
                this.adapter.notifyItemChanged(position)
            }
            RESULT.UNPIN -> if (data.isPinned) {
                data.isPinned = false
                this.adapter.notifyItemChanged(position)
            }
        }
    }

    enum class RESULT { UNPIN, SWIPE_LEFT }
}