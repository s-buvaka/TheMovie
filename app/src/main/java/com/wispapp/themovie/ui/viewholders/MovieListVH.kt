package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_category_movies.view.*

class MovieListVH(private val rootView: View) : BaseViewHolder<MovieList>(rootView),
    GenericAdapter.Binder<MovieList>, GenericAdapter.OnItemClickListener<MovieModel> {

    private val adapter = getMovieAdapter()

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieList>,
        listener: GenericAdapter.OnItemClickListener<MovieList>?
    ) {
        rootView.category_movie_recycler.adapter = adapter
        adapter.update(data.item.movies)
    }

    override fun onClickItem(data: MovieModel) {
        super.onClickItem(data)
    }

    private fun getMovieAdapter ()= object : GenericAdapter<MovieModel>(this) {
        override fun getItemId(position: Int): Long = getItem(position).item.id.toLong()

        override fun getLayoutId(position: Int, obj: MovieModel): Int =
            R.layout.item_movie_overview
    }
}