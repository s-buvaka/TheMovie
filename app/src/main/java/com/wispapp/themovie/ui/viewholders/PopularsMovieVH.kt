package com.wispapp.themovie.ui.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_overview.view.*
import org.koin.core.KoinComponent

class PopularsMovieVH(private val rootView: View) : BaseViewHolder<MovieOverviewModel>(rootView),
    GenericAdapter.Binder<MovieOverviewModel>, KoinComponent {

    override fun bind(
        data: GenericAdapter.DataWrapper<MovieOverviewModel>,
        listener: GenericAdapter.OnItemClickListener<MovieOverviewModel>?
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

    private fun setPoster(data: MovieOverviewModel) =
        ImageLoader.loadPoster(data.posterPath, rootView.poster_image)
}