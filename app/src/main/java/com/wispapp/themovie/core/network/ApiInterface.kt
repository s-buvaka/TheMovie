package com.wispapp.themovie.core.network

import com.wispapp.themovie.core.network.model.configs.ConfigResponse
import com.wispapp.themovie.core.network.model.movies.MovieResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("configuration")
    fun getConfigsAsync(): Deferred<Response<ConfigResponse>>

    @GET("movie/popular/")
    fun getPopularMoviesAsync(): Deferred<Response<MovieResponse>>
}