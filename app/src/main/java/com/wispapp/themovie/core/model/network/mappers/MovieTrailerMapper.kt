package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.MovieTrailersModel
import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.core.model.network.models.MovieTrailersResponse
import com.wispapp.themovie.core.model.network.models.TrailerResponse

class MovieTrailerResultMapper(private val mapper: Mapper<TrailerResponse, TrailerModel>) :
    Mapper<MovieTrailersResponse, MovieTrailersModel> {
    override fun mapFrom(source: MovieTrailersResponse): MovieTrailersModel =
        MovieTrailersModel(id = source.id, results = source.results.map { mapper.mapFrom(it) })
}

class MovieTrailerMapper : Mapper<TrailerResponse, TrailerModel> {
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