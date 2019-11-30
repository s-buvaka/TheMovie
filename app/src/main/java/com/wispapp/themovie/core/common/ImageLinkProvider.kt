package com.wispapp.themovie.core.common

import android.util.DisplayMetrics
import com.wispapp.themovie.core.application.App
import com.wispapp.themovie.core.model.database.models.ImagesConfigModel

abstract class ImageLinkProvider {

    abstract fun getUrl(): String
}

class DefaultImageLinkProvider(private val imageUrl: String) : ImageLinkProvider() {

    override fun getUrl(): String = imageUrl
}

class ApiConfigLinkProvider(
    private val imageUrl: String,
    private var configs: ImagesConfigModel
) : ImageLinkProvider() {

    private val displayMetrics: DisplayMetrics = App.applicationContext().resources.displayMetrics

    override fun getUrl(): String = createPosterUrl(imageUrl)

    private fun createPosterUrl(url: String): String =
        "${configs.secureBaseUrl}${getPosterSize()}$url"

    private fun getPosterSize(): String =
        configs.posterSizes[getSizeIndex(configs.posterSizes)]

    private fun getSizeIndex(sizeList: List<String>): Int {
        val size = sizeList.size
        val density = displayMetrics.density
        val isMDPI = density < 1.5
        val isHDPI = 1.5 <= density && density < 2
        val isXHDPI = 2 <= density && density < 3
        val isXXHDPI = 3 <= density

        return when {
            isMDPI -> if (size > 3) size - 4 else 0
            isHDPI -> if (size > 2) size - 3 else 0
            isXHDPI -> if (size > 1) size - 2 else 0
            isXXHDPI -> if (size > 0) size - 1 else 0
            else -> size
        }
    }
}