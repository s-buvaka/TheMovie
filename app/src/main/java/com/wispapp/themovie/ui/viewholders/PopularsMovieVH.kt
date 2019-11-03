package com.wispapp.themovie.ui.viewholders

import android.util.Log
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.MovieOverview
import com.wispapp.themovie.ui.base.recycler.BaseViewHolder
import com.wispapp.themovie.ui.base.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_overview.view.*
import java.lang.Exception

class PopularsMovieVH(private val rootView: View) : BaseViewHolder<MovieOverview>(rootView),
    GenericAdapter.Binder<MovieOverview> {

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

    private fun setPoster(data: MovieOverview) {
        Picasso.get()
            .load(data.posterPath)
            .placeholder(R.drawable.ic_favorite_border)
            .into(rootView.poster_image, object : Callback{
                override fun onSuccess() {
                    Log.d("XXX", "SUCCESSFUL")
                }

                override fun onError(e: Exception?) {
                   Log.d("XXX", "$e \n ${data.posterPath}")
                }
            })

    }
}