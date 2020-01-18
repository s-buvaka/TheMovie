package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.network.models.MovieResponse
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse

class MovieResultMapper(private val mapper: Mapper<MovieResponse, MovieModel>) :
    Mapper<MoviesResultResponse, MoviesResultModel> {

    override fun mapFrom(source: MoviesResultResponse): MoviesResultModel =
        MoviesResultModel(results = source.results.map { mapper.mapFrom(it) })
}

class MoviesMapper(private val category: CATEGORY) : Mapper<MovieResponse, MovieModel> {

    override fun mapFrom(source: MovieResponse): MovieModel =
        MovieModel(
            id = source.id,
            title = source.title,
            originalTitle = source.originalTitle,
            overview = source.overview,
            popularity = source.popularity,
            originalLanguage = source.originalLanguage,
            hasVideo = source.hasVideo,
            genreIds = source.genreIds,
            posterPath = source.posterPath ?: "",
            backdropPath = source.backdropPath ?: "",
            releaseDate = source.releaseDate,
            isAdult = source.isAdult,
            voteAverage = source.voteAverage,
            voteCount = source.voteCount,
            category = mutableSetOf(category)
        )
}