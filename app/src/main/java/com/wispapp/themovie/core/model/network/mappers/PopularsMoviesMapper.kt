package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.network.models.movies.MovieOverviewResponse
import com.wispapp.themovie.core.model.network.models.movies.MoviesResultResponse

class MoviesResultMapper(private val mapper: Mapper<MovieOverviewResponse, MovieOverviewModel>) :
    Mapper<MoviesResultResponse, MoviesResultModel> {

    override fun mapFrom(source: MoviesResultResponse?): MoviesResultModel? =
        source?.let {
            MoviesResultModel(
                page = it.page,
                totalResults = it.totalResults,
                totalPages = it.totalPages,
                results = it.results.map { movieOverview -> mapper.mapFrom(movieOverview)!! }
            )
        }
}

class MoviesOverviewMapper : Mapper<MovieOverviewResponse, MovieOverviewModel> {

    override fun mapFrom(source: MovieOverviewResponse?): MovieOverviewModel? =
        source?.let {
            MovieOverviewModel(
                id = it.id,
                title = it.title,
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = it.popularity,
                originalLanguage = it.originalLanguage,
                hasVideo = it.hasVideo,
                genreIds = it.genreIds,
                posterPath = it.posterPath,
                backdropPath = it.backdropPath,
                releaseDate = it.releaseDate,
                isAdult = it.isAdult,
                voteAverage = it.voteAverage,
                voteCount = it.voteCount
            )
        }
}