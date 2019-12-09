package com.wispapp.themovie.core.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.wispapp.themovie.R
import org.koin.core.KoinComponent

interface ImageLoader {

    fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView)

    fun loadBluredImage(linkProvider: ImageLinkProvider, targetView: ImageView, blurRadius: Int)
}

class ImageLoaderImpl : ImageLoader, KoinComponent {

    override fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView) {
        Glide
            .with(targetView)
            .load(linkProvider.getUrl())
            .placeholder(R.drawable.poster_paceholder)
            .into(targetView)
    }

    override fun loadBluredImage(
        linkProvider: ImageLinkProvider,
        targetView: ImageView,
        blurRadius: Int
    ) {
        Glide
            .with(targetView)
            .load(linkProvider.getUrl())
            .apply(bitmapTransform(jp.wasabeef.glide.transformations.BlurTransformation(blurRadius, 3)))
            .into(targetView)
    }
}