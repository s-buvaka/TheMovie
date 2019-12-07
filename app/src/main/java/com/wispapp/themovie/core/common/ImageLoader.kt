package com.wispapp.themovie.core.common

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import com.wispapp.themovie.core.application.App
import jp.wasabeef.picasso.transformations.BlurTransformation
import org.koin.core.KoinComponent

interface ImageLoader {

    fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView)

    fun loadBluredImage(linkProvider: ImageLinkProvider, targetView: ImageView, blurRadius: Int)
}

class ImageLoaderImpl(private val picasso: Picasso) : ImageLoader, KoinComponent {

    override fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView) {
        picasso
            .load(linkProvider.getUrl())
            .placeholder(R.drawable.poster_paceholder)
            .into(targetView)
    }

    override fun loadBluredImage(
        linkProvider: ImageLinkProvider,
        targetView: ImageView,
        blurRadius: Int
    ) {
        picasso
            .load(linkProvider.getUrl())
            .transform(BlurTransformation(App.applicationContext(), blurRadius))
            .into(targetView)
    }
}