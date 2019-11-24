package com.wispapp.themovie.ui.recycler.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.ui.recycler.BaseViewHolder
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieVH(private val rootView: View) : BaseViewHolder<MovieModel>(rootView),
    GenericAdapter.Binder<MovieModel>, KoinComponent {

    private val imageLoader: ImageLoader by inject()

    override fun bind(data: MovieModel, listener: GenericAdapter.OnItemClickListener<MovieModel>?) {
        setPoster(data)
        rootView.title_text.text = data.title
        rootView.setOnClickListener { listener?.onClickItem(data) }
    }

    private fun setPoster(data: MovieModel) =
        imageLoader.loadPoster(data.posterPath, rootView.poster_image)
}