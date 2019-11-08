package com.wispapp.themovie.core.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.network.model.NetworkException
import com.wispapp.themovie.core.network.model.movies.MovieResponse
import retrofit2.Response

abstract class BaseNetworkProvider<E, T>(/*private val mapper: Mapper<E, T>*/) {

    suspend fun <T : Any> safeApiCall(
        networkCall: suspend () -> Response<T>,
        errorFunc: (exception: NetworkException) -> Unit
    ): T? {
        var data: T? = null
        val response = networkCall.invoke()

        if (response.isSuccessful)
            data = response.body()
        else
            handleError(response, errorFunc)

        return data
    }

    private fun <T : Any> handleError(
        response: Response<T>,
        errorFunc: (exception: NetworkException) -> Unit
    ) {
        val networkException = mapToNetworkException(response)
        errorFunc.invoke(networkException)
    }

    private fun <T> mapToNetworkException(response: Response<T>): NetworkException {
        val type = object : TypeToken<NetworkException>() {}.type
        return Gson().fromJson(response.errorBody()!!.charStream(), type)
    }
}

class PopularMoviesProvider(private val api: ApiInterface) : BaseNetworkProvider<String, String>() {

    suspend fun getPopularMovies(): MovieResponse? {
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