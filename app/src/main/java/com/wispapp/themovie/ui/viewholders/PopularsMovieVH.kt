package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.network.model.movies.MovieOverview
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_overview.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class PopularsMovieVH(private val rootView: View) : BaseViewHolder<MovieOverview>(rootView),
    GenericAdapter.Binder<MovieOverview>, KoinComponent {

    private val imageLoader: ImageLoader by inject()

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieOverview>,
        listener: GenericAdapter.OnItemClickListener<MovieOverview>?
    ) {
        super.bind(data, listener)
        setPoster(data.item)
        rootView.apply {
            title_text.text = data.item.title
            date_text.text = data.item.releaseDate
            overview_text.text = data.item.overview
            more_text_view.setOnClickListener { listener?.onClickItem(data.item) }
        }
    }

    private fun setPoster(data: MovieOverview) =
        imageLoader.loadImage(data.posterPath, rootView.poster_image)
}