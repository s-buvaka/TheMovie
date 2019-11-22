package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_category_title.view.*

interface Categories

data class MovieCategory(val category: CATEGORY) : Categories

data class MovieList(val movies: List<MovieModel>) : Categories

class MovieCategoryVH(private val rootView: View) : BaseViewHolder<MovieCategory>(rootView),
    GenericAdapter.Binder<MovieCategory> {

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieCategory>,
        listener: GenericAdapter.OnItemClickListener<MovieCategory>?
    ) {
        rootView.category_text.text = getCategoryName(data.item.category)
    }

    private fun getCategoryName(category: CATEGORY) =
        when (category) {
            CATEGORY.NOW_PLAYING -> rootView.context.getString(R.string.main_category_now_playing)
            CATEGORY.POPULAR -> rootView.context.getString(R.string.main_category_popular)
            CATEGORY.TOP_RATED -> rootView.context.getString(R.string.main_category_top_rated)
            CATEGORY.UPCOMING -> rootView.context.getString(R.string.main_category_upcoming)
        }
}

