package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.database.models.PopularMoviesModel
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse
import com.wispapp.themovie.core.model.network.models.PopularMoviesResponse

class MovieResultMapper(private val mapper: Mapper<PopularMoviesResponse, PopularMoviesModel>) :
    Mapper<MoviesResultResponse, MoviesResultModel> {

    override fun mapFrom(source: MoviesResultResponse): MoviesResultModel =
        MoviesResultModel(results = source.results.map { mapper.mapFrom(it) })
}

class PopularMoviesMapper : Mapper<PopularMoviesResponse, PopularMoviesModel> {

    override fun mapFrom(source: PopularMoviesResponse): PopularMoviesModel =
        PopularMoviesModel(
                id = source.id,
                title = source.title,
                originalTitle = source.originalTitle,
                overview = source.overview,
                popularity = source.popularity,
                originalLanguage = source.originalLanguage,
                hasVideo = source.hasVideo,
                genreIds = source.genreIds,
                posterPath = source.posterPath,
                backdropPath = source.backdropPath,
                releaseDate = source.releaseDate,
                isAdult = source.isAdult,
                voteAverage = source.voteAverage,
                voteCount = source.voteCount
            )
}