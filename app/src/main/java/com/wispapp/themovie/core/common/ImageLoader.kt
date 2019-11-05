package com.wispapp.themovie.core.common

import android.widget.ImageView

interface ImageLoader {

    fun loadImage(url: String, targetView: ImageView)
}