package com.wispapp.themovie.ui.moviedetails.viewholders

import android.view.View
import com.wispapp.themovie.core.common.ApiConfigLinkProvider
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.common.ImageLinkProvider
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.ui.recycler.BaseViewHolder
import com.wispapp.themovie.ui.recycler.GenericAdapter
import kotlinx.android.synthetic.main.item_fullscreen_image.view.*
import kotlinx.android.synthetic.main.item_movie_image.view.movie_photo
import org.koin.core.KoinComponent
import org.koin.core.inject

class FullScreenImageVH(private val rootView: View) : BaseViewHolder<ImageModel>(rootView),
    KoinComponent {

    private val imageLoader: ImageLoader by inject()
    private val configs = ConfigsHolder.getConfig()

    override fun bind(data: ImageModel, listener: GenericAdapter.OnItemClickListener<ImageModel>?) {
        val linkProvider = ApiConfigLinkProvider(data.filePath, configs)

        setImage(linkProvider)
        setBackground(linkProvider)
    }

    private fun setImage(linkProvider: ImageLinkProvider) {
        imageLoader.loadPoster(linkProvider, rootView.movie_photo)
    }

    private fun setBackground(linkProvider: ImageLinkProvider) {
        imageLoader.loadBluredImage(linkProvider, rootView.image_background, 19)
    }
}