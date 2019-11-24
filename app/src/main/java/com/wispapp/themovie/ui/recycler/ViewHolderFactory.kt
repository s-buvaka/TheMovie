package com.wispapp.themovie.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.recycler.viewholders.MovieVH

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<T>

object ViewHolderFactory {

    fun create(view: View, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            R.layout.item_movie -> MovieVH(view)
            else -> throw Exception("Wrong view type")
        }
}