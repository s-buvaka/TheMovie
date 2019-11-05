package com.wispapp.themovie.core.mappers

import com.wispapp.themovie.core.database.model.ConfigModel
import com.wispapp.themovie.core.database.model.ImagesModel
import com.wispapp.themovie.core.network.model.configs.ConfigResponse
import com.wispapp.themovie.core.network.model.configs.Images

class ConfigMapper : Mapper<ConfigModel, ConfigResponse> {

    override fun mapFromDao(dao: ConfigModel): ConfigResponse =
        ConfigResponse(images = dao.images, changeKeys = dao.changeKeys)

    override fun mapToDao(response: ConfigResponse): ConfigModel =
        ConfigModel(images = response.images, changeKeys = response.changeKeys)
}

class ImagesConfigMapper : Mapper<ImagesModel, Images> {
    override fun mapFromDao(dao: ImagesModel): Images =
        Images(
            baseUrl = dao.baseUrl,
            secureBaseUrl = dao.secureBaseUrl,
            posterSizes = dao.posterSizes,
            backdropSizes = dao.backdropSizes,
            logoSizes = dao.logoSizes,
            stillSizes = dao.stillSizes,
            profileSizes = dao.profileSizes
        )

    override fun mapToDao(response: Images): ImagesModel =
        ImagesModel(
            baseUrl = response.baseUrl,
            secureBaseUrl = response.secureBaseUrl,
            posterSizes = response.posterSizes,
            backdropSizes = response.backdropSizes,
            logoSizes = response.logoSizes,
            stillSizes = response.stillSizes,
            profileSizes = response.profileSizes
        )
}