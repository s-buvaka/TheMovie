package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.PopularMoviesModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_overview.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class PopularsMovieVH(private val rootView: View) : BaseViewHolder<PopularMoviesModel>(rootView),
    GenericAdapter.Binder<PopularMoviesModel>, KoinComponent {

    private val imageLoader: ImageLoader by inject()

    override fun bind(
        data: GenericAdapter.DataWrapper<PopularMoviesModel>,
        listener: GenericAdapter.OnItemClickListener<PopularMoviesModel>?
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

    private fun setPoster(data: PopularMoviesModel) =
        imageLoader.loadPoster(data.posterPath, rootView.poster_image)
}