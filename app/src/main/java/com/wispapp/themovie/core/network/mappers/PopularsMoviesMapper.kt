package com.wispapp.themovie.core.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.database.model.MovieOverviewDao
import com.wispapp.themovie.core.database.model.MoviesResultDao
import com.wispapp.themovie.core.network.model.movies.MovieOverviewResponse
import com.wispapp.themovie.core.network.model.movies.MoviesResultResponse

class MoviesResultMapper(private val mapper: Mapper<MovieOverviewResponse, MovieOverviewDao>) :
    Mapper<MoviesResultResponse, MoviesResultDao> {

    override fun mapFrom(source: MoviesResultResponse?): MoviesResultDao? =
        source?.let {
            MoviesResultDao(
                page = it.page,
                totalResults = it.totalResults,
                totalPages = it.totalPages,
                results = it.results.map { movieOverview -> mapper.mapFrom(movieOverview)!! }
            )
        }
}

class MoviesOverviewMapper : Mapper<MovieOverviewResponse, MovieOverviewDao> {
    override fun mapFrom(source: MovieOverviewResponse?): MovieOverviewDao? =
        source?.let {
            MovieOverviewDao(
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