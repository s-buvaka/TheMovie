package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_overview.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class PopularsMovieVH(private val rootView: View) : BaseViewHolder<MovieModel>(rootView),
    GenericAdapter.Binder<MovieModel>, KoinComponent {

    private val imageLoader: ImageLoader by inject()

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieModel>,
        listener: GenericAdapter.OnItemClickListener<MovieModel>?
    ) {
        super.bind(data, listener)
        setPoster(data.item)
        rootView.title_text.text = data.item.title
        rootView.setOnClickListener { listener?.onClickItem(data.item) }

    }

    private fun setPoster(data: MovieModel) =
        imageLoader.loadPoster(data.posterPath, rootView.poster_image)
}