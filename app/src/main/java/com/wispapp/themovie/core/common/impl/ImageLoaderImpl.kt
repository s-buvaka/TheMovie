package com.wispapp.themovie.core.common.impl

import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import com.wispapp.themovie.core.common.ImageLoader

class ImageLoaderImpl(private val picasso: Picasso) : ImageLoader {

    override fun loadImage(url: String, targetView: ImageView) {
        picasso
            .load(url)
            .placeholder(R.drawable.image_placeholder)
            .into(targetView)
    }
}