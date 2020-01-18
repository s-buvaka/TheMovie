package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.core.model.database.models.ImagesResultModel
import com.wispapp.themovie.core.model.network.models.ImageResponse
import com.wispapp.themovie.core.model.network.models.ImagesResultResponse

class ImagesResultMapper(private val mapper: Mapper<ImageResponse, ImageModel>) :
    Mapper<ImagesResultResponse, ImagesResultModel> {

    override fun mapFrom(source: ImagesResultResponse): ImagesResultModel =
        ImagesResultModel(
            id = source.id,
            backdrops = source.backdrops.map { mapper.mapFrom(it) },
            posters = source.posters.map { mapper.mapFrom(it) }
        )
}

class ImagesMapper : Mapper<ImageResponse, ImageModel> {

    override fun mapFrom(source: ImageResponse): ImageModel =
        ImageModel(
            aspectRatio = source.aspectRatio,
            filePath = source.filePath,
            iso6391 = source.iso6391,
            voteAverage = source.voteAverage,
            voteCount = source.voteCount,
            width = source.width,
            height = source.height
        )
}