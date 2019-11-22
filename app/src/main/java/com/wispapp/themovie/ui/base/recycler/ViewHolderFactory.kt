package com.wispapp.themovie.ui.base.recycler

import android.view.View
import com.wispapp.themovie.R
import com.wispapp.themovie.ui.viewholders.MovieCategoryVH
import com.wispapp.themovie.ui.viewholders.MovieListVH
import com.wispapp.themovie.ui.viewholders.PopularsMovieVH

object ViewHolderFactory {

    fun create(view: View, viewType: Int): BaseViewHolder<*> =
        when (viewType) {
            R.layout.item_movie_overview -> PopularsMovieVH(view)
            R.layout.item_category_movies -> MovieListVH(view)
            R.layout.item_category_title -> MovieCategoryVH(view)
            else -> throw Exception("Wrong view type")
        }
}