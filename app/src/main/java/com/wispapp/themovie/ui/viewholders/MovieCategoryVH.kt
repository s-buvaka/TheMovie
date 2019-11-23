package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_category_movies.view.*
import kotlinx.android.synthetic.main.item_category_title.view.*

interface Categories {

    val movieId: Int
}

data class MovieCategory(val category: CATEGORY, override val movieId: Int = 0) : Categories

data class MovieList(val movies: List<MovieModel>, override val movieId: Int = 0) : Categories

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

class MovieListVH(private val rootView: View) : BaseViewHolder<MovieList>(rootView),
    GenericAdapter.Binder<MovieList>, GenericAdapter.OnItemClickListener<MovieModel> {

    private val adapter = getMovieAdapter()
    private var itemListener: GenericAdapter.OnItemClickListener<MovieList>? = null

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieList>,
        listener: GenericAdapter.OnItemClickListener<MovieList>?
    ) {
        itemListener = listener
        rootView.category_movie_recycler.adapter = adapter
        adapter.update(data.item.movies)
    }

    //TODO Подумать как лучше отрабатывать клик по фильму
    override fun onClickItem(data: MovieModel) {
        itemListener?.onClickItem(MovieList(emptyList(), data.id))
    }

    private fun getMovieAdapter() = object : GenericAdapter<MovieModel>(this) {
        override fun getItemId(position: Int): Long = getItem(position).item.id.toLong()

        override fun getLayoutId(position: Int, obj: MovieModel): Int =
            R.layout.item_movie
    }
}

