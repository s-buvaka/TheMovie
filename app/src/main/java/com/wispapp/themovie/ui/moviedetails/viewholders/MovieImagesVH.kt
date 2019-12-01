package com.wispapp.themovie.ui.moviedetails.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ApiConfigLinkProvider
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.ui.recycler.BaseViewHolder
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_movie_image.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject

class MovieImagesVH(private val rootView: View) : BaseViewHolder<ImageModel>(rootView),
    KoinComponent {

    private val imageLoader: ImageLoader by inject()
    private val configs = ConfigsHolder.getConfig()

    override fun bind(data: ImageModel, listener: GenericAdapter.OnItemClickListener<ImageModel>?) {
        setImage(data)
        rootView.setOnClickListener { listener?.onClickItem(data) }
    }

    private fun setImage(data: ImageModel) {
        val linkProvider = ApiConfigLinkProvider(data.filePath, configs)
        imageLoader.loadPoster(linkProvider, rootView.movie_photo)
    }
}