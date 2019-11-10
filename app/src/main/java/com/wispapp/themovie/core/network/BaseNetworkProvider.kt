package com.wispapp.themovie.core.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.database.model.MoviesResultDao
import com.wispapp.themovie.core.network.model.NetworkException
import com.wispapp.themovie.core.network.model.movies.MoviesResultResponse
import retrofit2.Response

abstract class BaseNetworkProvider<RESPONSE, MODEL>(private val mapper: Mapper<RESPONSE, MODEL>) {

    suspend fun safeApiCall(
        networkCall: suspend () -> Response<RESPONSE>,
        errorFunc: (exception: NetworkException) -> Unit
    ): MODEL? {
        var data: RESPONSE? = null
        val response = networkCall.invoke()

        if (response.isSuccessful)
            data = response.body()
        else
            handleError(response, errorFunc)

        return mapper.mapFrom(data)
    }

    private fun handleError(
        response: Response<RESPONSE>,
        errorFunc: (exception: NetworkException) -> Unit
    ) {
        val networkException = mapToNetworkException(response)
        errorFunc.invoke(networkException)
    }

    private fun mapToNetworkException(response: Response<RESPONSE>): NetworkException {
        val type = object : TypeToken<NetworkException>() {}.type
        return Gson().fromJson(response.errorBody()!!.charStream(), type)
    }
}

class PopularMoviesProvider(
    mapper: Mapper<MoviesResultResponse, MoviesResultDao>,
    private val api: ApiInterface
) :
    BaseNetworkProvider<MoviesResultResponse, MoviesResultDao>(mapper) {

    suspend fun getPopularMovies(): MoviesResultDao? {
        return safeApiCall(
            networkCall = { api.getPopularMoviesAsync().await() },
            errorFunc = { errorException -> handleError(errorException) })
    }

    private fun handleError(exception: NetworkException) {
        Log.d("XXX", exception.statusMessage)
        Log.d("XXX", exception.statusCode.toString())
        Log.d("XXX", exception.isSuccessful.toString())
    }
}