package com.wispapp.themovie.core.model.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.network.models.NetworkException
import com.wispapp.themovie.core.model.network.models.movies.MovieOverviewResponse
import com.wispapp.themovie.core.model.network.models.movies.MoviesResultResponse
import retrofit2.Response

interface NetworkProvider<T> {

    suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        vararg: RequestWrapper
    ): List<T>
}

abstract class BaseNetworkProvider<RESPONSE, MODEL> :
    NetworkProvider<MODEL> {

    protected fun parseResponse(
        response: Response<RESPONSE>,
        errorFunc: (exception: NetworkException) -> Unit
    ): RESPONSE? {
        var data: RESPONSE? = null

        if (response.isSuccessful)
            data = response.body()
        else
            handleError(response, errorFunc)

        return data
    }

    abstract fun mapData(source: RESPONSE): List<MODEL>

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
    private val mapper: Mapper<MovieOverviewResponse, MovieOverviewModel>,
    private val api: ApiInterface
) : BaseNetworkProvider<MoviesResultResponse, MovieOverviewModel>(),
    NetworkProvider<MovieOverviewModel> {

    override suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        vararg: RequestWrapper
    ): List<MovieOverviewModel> {
        val response = parseResponse(
            response = api.getPopularMoviesAsync().await(),
            errorFunc = { errorException -> errorFunc(errorException) })

        if (response != null)
            return mapData(response)
        else
            throw NetworkException(statusMessage = "Generated Network Error Something went wrong")
    }

    override fun mapData(source: MoviesResultResponse): List<MovieOverviewModel> =
        source.results.map { mapper.mapFrom(it) }

}