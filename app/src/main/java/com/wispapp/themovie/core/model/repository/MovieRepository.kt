package com.wispapp.themovie.core.model.repository

import com.wispapp.themovie.core.model.network.models.movies.MovieOverviewResponse

interface MovieRepository {

    suspend fun getPopularMovie(): List<MovieOverviewResponse>
}