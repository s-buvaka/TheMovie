package com.wispapp.themovie.ui.recycler

import androidx.recyclerview.widget.DiffUtil

class GenericDiffUtil<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val itemDiff: GenericAdapter.GenericItemDiff<T>
) :
    DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemDiff.isSame(oldItems, newItems, oldItemPosition, newItemPosition)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        itemDiff.isSameContent(oldItems, newItems, oldItemPosition, newItemPosition)
}