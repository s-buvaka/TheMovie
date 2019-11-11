package com.wispapp.themovie.core.model.network

import com.wispapp.themovie.core.model.network.models.ConfigResponse
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("configuration")
    fun getConfigsAsync(): Deferred<Response<ConfigResponse>>

    @GET("movie/popular/")
    fun getPopularMoviesAsync(): Deferred<Response<MoviesResultResponse>>
}