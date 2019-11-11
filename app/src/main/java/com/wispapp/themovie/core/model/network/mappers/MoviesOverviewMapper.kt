package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.network.models.MovieOverviewResponse

class MoviesOverviewMapper : Mapper<MovieOverviewResponse, MovieOverviewModel> {

    override fun mapFrom(source: MovieOverviewResponse): MovieOverviewModel =
            MovieOverviewModel(
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