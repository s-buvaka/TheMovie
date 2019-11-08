package com.wispapp.themovie.core.common.impl

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import com.wispapp.themovie.core.common.ImageLoader
import org.koin.core.KoinComponent
import org.koin.core.inject

class ImageLoaderImpl : ImageLoader, KoinComponent {

    private val picasso: Picasso by inject()

    override fun loadImage(url: String, targetView: ImageView) {
        picasso
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(targetView)
    }
}