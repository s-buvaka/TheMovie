package com.wispapp.themovie.core.repository

import com.wispapp.themovie.core.model.MovieOverview

interface MovieRepository {

    suspend fun getPopularMovie(): List<MovieOverview>
}