package com.wispapp.themovie.core.model.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.network.models.*
import retrofit2.Response

interface NetworkProvider<T> {

    suspend fun get(
        args: RequestWrapper? = null,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<T>
}

abstract class BaseRemoteProvider<RESPONSE, MODEL> :
    NetworkProvider<MODEL> {

    protected fun parseResponse(
        response: Response<RESPONSE>,
        errorFunc: (exception: NetworkException) -> Unit
    ): RESPONSE {
        var data: RESPONSE? = null

        if (response.isSuccessful)
            data = response.body()
        else
            handleError(response, errorFunc)

        if (data != null)
            return data
        else
            throw NetworkException(statusMessage = "Generated Network Error Something went wrong")
    }

    protected abstract fun mapData(source: RESPONSE): List<MODEL>

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

class ConfigsRemoteProvider(
    private val mapper: Mapper<ConfigResponse, ConfigModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<ConfigResponse, ConfigModel>(),
    NetworkProvider<ConfigModel> {

    override suspend fun get(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<ConfigModel> {
        val response = parseResponse(
            response = api.getConfigsAsync().await(),
            errorFunc = { errorException -> errorFunc(errorException) }
        )

        return mapData(response)
    }

    override fun mapData(source: ConfigResponse): List<ConfigModel> =
        listOf(mapper.mapFrom(source))
}

class PopularMoviesRemoteProvider(
    private val mapper: Mapper<MovieOverviewResponse, MovieOverviewModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<MoviesResultResponse, MovieOverviewModel>(),
    NetworkProvider<MovieOverviewModel> {

    override suspend fun get(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<MovieOverviewModel> {
        val response = parseResponse(
            response = api.getPopularMoviesAsync().await(),
            errorFunc = { errorException -> errorFunc(errorException) })

        return mapData(response)
    }

    override fun mapData(source: MoviesResultResponse): List<MovieOverviewModel> =
        source.results.map { mapper.mapFrom(it) }

}

class MoviesDetailsProvider(
    private val mapper: Mapper<MovieDetailsResponse, MovieDetailsModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<MovieDetailsResponse, MovieDetailsModel>(),
    NetworkProvider<MovieDetailsModel> {

    override suspend fun get(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<MovieDetailsModel> {

        val movieId = if (args is MovieId) args.movieId
        else throw IllegalArgumentException("Invalid object type received")

        val response = parseResponse(
            response = api.searchByIdAsync(movieId).await(),
            errorFunc = { errorException -> errorFunc(errorException) }
        )

        return mapData(response)
    }

    override fun mapData(source: MovieDetailsResponse): List<MovieDetailsModel> =
        listOf(mapper.mapFrom(source))
}