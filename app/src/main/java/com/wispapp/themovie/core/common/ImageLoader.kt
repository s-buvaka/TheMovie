package com.wispapp.themovie.core.common

import android.util.DisplayMetrics
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.wispapp.themovie.R
import com.wispapp.themovie.core.model.database.models.ImagesConfigModel
import org.koin.core.KoinComponent

interface ImageLoader {

    fun setConfigs(_configs: ImagesConfigModel)

    fun loadPoster(url: String, targetView: ImageView)
}

private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"
private const val BASE_SECURE_IMAGE_URL = "https://image.tmdb.org/t/p/"
private const val ORIGINAL_SIZE = "original"

class ImageLoaderImpl(
    private val picasso: Picasso,
    private val displayMetrics: DisplayMetrics
) : ImageLoader, KoinComponent {


    private var configs: ImagesConfigModel? = null

    override fun setConfigs(_configs: ImagesConfigModel) {
        configs = _configs
    }

    override fun loadPoster(url: String, targetView: ImageView) {
        picasso
            .load(createPosterUrl(url))
            .placeholder(R.drawable.poster_paceholder)
            .into(targetView)
    }

    private fun createPosterUrl(url: String): String =
        "${getConfig().secureBaseUrl}${getPosterSize()}$url"

    private fun getPosterSize(): String =
        getConfig().posterSizes[getSizeIndex(getConfig().posterSizes)]

    private fun getSizeIndex(sizeList: List<String>): Int {
        val size = sizeList.size
        val density = displayMetrics.density
        val isMDPI = density < 1.5
        val isHDPI = 1.5 <= density && density < 2
        val isXHDPI = 2 <= density && density < 3
        val isXXHDPI = 3 <= density

        return when {
            isMDPI -> if (size >= 3) size - 4 else 0
            isHDPI -> if (size >= 2) size - 3 else 0
            isXHDPI -> if (size >= 1) size - 2 else 0
            isXXHDPI -> size - 1
            else -> size
        }
    }

    private fun getConfig() =
        configs ?: ImagesConfigModel(
            baseUrl = BASE_IMAGE_URL,
            secureBaseUrl = BASE_SECURE_IMAGE_URL,
            posterSizes = listOf(ORIGINAL_SIZE),
            backdropSizes = listOf(ORIGINAL_SIZE),
            logoSizes = listOf(ORIGINAL_SIZE),
            stillSizes = listOf(ORIGINAL_SIZE),
            profileSizes = listOf(ORIGINAL_SIZE)
        )
}