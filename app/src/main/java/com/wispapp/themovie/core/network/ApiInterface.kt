package com.wispapp.themovie.core.network

import com.wispapp.themovie.core.model.MoviewResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {

    @GET("movie/popular/")
    fun getPopularMoviesAsync():Deferred<Response<MoviewResponse>>
}