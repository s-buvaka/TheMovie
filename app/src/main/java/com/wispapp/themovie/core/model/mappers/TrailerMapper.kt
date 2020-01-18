package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.core.model.database.models.TrailersResultModel
import com.wispapp.themovie.core.model.network.models.TrailerResponse
import com.wispapp.themovie.core.model.network.models.TrailersResultResponse

class TrailerResultMapper(private val mapper: Mapper<TrailerResponse, TrailerModel>) :
    Mapper<TrailersResultResponse, TrailersResultModel> {
    override fun mapFrom(source: TrailersResultResponse): TrailersResultModel =
        TrailersResultModel(id = source.id, results = source.results.map { mapper.mapFrom(it) })
}

class TrailerMapper : Mapper<TrailerResponse, TrailerModel> {
    override fun mapFrom(source: TrailerResponse): TrailerModel =
        TrailerModel(
            id = source.id,
            iso6391 = source.iso6391,
            iso31661 = source.iso31661,
            key = source.key,
            name = source.name,
            site = source.site,
            size = source.size,
            type = source.type
        )
}