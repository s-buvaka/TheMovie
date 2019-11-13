package com.wispapp.themovie.core.model.network

import com.wispapp.themovie.core.model.network.models.ConfigResponse
import com.wispapp.themovie.core.model.network.models.MovieDetailsResponse
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("configuration")
    fun getConfigsAsync(): Deferred<Response<ConfigResponse>>

    @GET("movie/popular/")
    fun getPopularMoviesAsync(): Deferred<Response<MoviesResultResponse>>

    @GET("movie/{id}?")
    fun searchByIdAsync(@Path("id") movieId: Int): Deferred<Response<MovieDetailsResponse>>
}