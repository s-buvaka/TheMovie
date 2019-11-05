package com.wispapp.themovie.core.repository.impl

import com.wispapp.themovie.core.network.model.movies.MovieOverview
import com.wispapp.themovie.core.network.ApiInterface
import com.wispapp.themovie.core.repository.BaseRepository
import com.wispapp.themovie.core.repository.MovieRepository

class MovieRepositoryImpl(private val api: ApiInterface) : BaseRepository(), MovieRepository {

    override suspend fun getPopularMovie(): List<MovieOverview> {
        val popularMovie = safeApiCall(
            call = { api.getPopularMoviesAsync().await() },
            errorMessage = ""
        )
        return popularMovie?.results ?: emptyList()
    }
}