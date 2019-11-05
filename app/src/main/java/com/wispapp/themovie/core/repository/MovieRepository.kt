package com.wispapp.themovie.core.repository

import com.wispapp.themovie.core.network.model.movies.MovieOverview

interface MovieRepository {

    suspend fun getPopularMovie(): List<MovieOverview>
}