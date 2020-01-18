package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.ImagesConfigModel
import com.wispapp.themovie.core.model.network.models.ConfigResponse
import com.wispapp.themovie.core.model.network.models.ImagesConfigResponse

class ConfigsMapper(private val imageConfigMapper: Mapper<ImagesConfigResponse, ImagesConfigModel>) :
    Mapper<ConfigResponse, ConfigModel> {

    override fun mapFrom(source: ConfigResponse): ConfigModel =
        ConfigModel(
            imagesConfig = imageConfigMapper.mapFrom(source.images),
            changeKeys = source.changeKeys
        )
}

class ImageConfigMapper : Mapper<ImagesConfigResponse, ImagesConfigModel> {

    override fun mapFrom(source: ImagesConfigResponse): ImagesConfigModel =
        ImagesConfigModel(
            baseUrl = source.baseUrl,
            secureBaseUrl = source.secureBaseUrl,
            posterSizes = source.posterSizes,
            backdropSizes = source.backdropSizes,
            logoSizes = source.logoSizes,
            stillSizes = source.stillSizes,
            profileSizes = source.profileSizes
        )
}