package com.wispapp.themovie.core.common

import com.wispapp.themovie.core.model.database.models.ImagesConfigModel

object ConfigsHolder {

    private const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/"
    private const val BASE_SECURE_IMAGE_URL = "https://image.tmdb.org/t/p/"
    private const val ORIGINAL_SIZE = "original"

    private var configs: ImagesConfigModel? = null

    fun setConfigs(_configs: ImagesConfigModel) {
        configs = _configs
    }

    fun getConfig() =
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