package com.wispapp.themovie.core.repository

import com.wispapp.themovie.core.network.model.movies.MovieOverviewResponse

interface MovieRepository {

    suspend fun getPopularMovie(): List<MovieOverviewResponse>
}