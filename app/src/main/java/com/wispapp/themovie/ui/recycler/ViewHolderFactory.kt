package com.wispapp.themovie.ui.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.main.viewholders.MovieVH
import com.wispapp.themovie.ui.main.viewholders.SearchMovieVH
import com.wispapp.themovie.ui.moviedetails.MovieImagesVH

abstract class BaseViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    GenericAdapter.Binder<T>

object ViewHolderFactory {

    fun create(view: View, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            R.layout.item_movie -> MovieVH(view)
            R.layout.item_search_movie -> SearchMovieVH(view)
            R.layout.item_movie_photo -> MovieImagesVH(view)
            else -> throw Exception("Wrong view type")
        }
}