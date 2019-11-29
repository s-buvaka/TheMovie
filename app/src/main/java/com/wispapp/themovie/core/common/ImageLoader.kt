package com.wispapp.themovie.core.common

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import org.koin.core.KoinComponent

interface ImageLoader {

    fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView)
}

class ImageLoaderImpl(private val picasso: Picasso) : ImageLoader, KoinComponent {

    override fun loadPoster(linkProvider: ImageLinkProvider, targetView: ImageView) {
        picasso
            .load(linkProvider.getUrl())
            .placeholder(R.drawable.poster_paceholder)
            .into(targetView)
    }
}