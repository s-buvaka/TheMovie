package com.wispapp.themovie.core.model.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.network.models.ConfigResponse
import com.wispapp.themovie.core.model.network.models.MovieDetailsResponse
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse
import com.wispapp.themovie.core.model.network.models.NetworkException
import retrofit2.Response
import java.lang.Exception
import java.net.ConnectException
import java.net.UnknownHostException

interface NetworkProvider<T> {

    suspend fun get(
        args: ArgumentsWrapper? = null,
        errorFunc: (exception: NetworkException) -> Unit
    ): T
}

abstract class BaseRemoteProvider<RESPONSE, MODEL>(
    private val mapper: Mapper<RESPONSE, MODEL>
) :
    NetworkProvider<MODEL> {

    abstract suspend fun getResponse(args: ArgumentsWrapper?): Response<RESPONSE>

    override suspend fun get(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): MODEL {
        try {
            return parseResponse(
                response = getResponse(args),
                errorFunc = { errorException -> errorFunc(errorException) }
            )
        } catch (e: UnknownHostException) {
            //TODO ПРодумать как обработать ошибки сети
            e.printStackTrace()
            Log.d("XXX", "NOT INTERNET")
        } catch (e: ConnectException){
            e.printStackTrace()
            Log.d("XXX", "NOT INTERNET")
        }
        throw Exception("LALAL")
    }

    private fun parseResponse(
        response: Response<RESPONSE>,
        errorFunc: (exception: NetworkException) -> Unit
    ): MODEL {
        var data: RESPONSE? = null

        if (response.isSuccessful)
            data = response.body()
        else
            handleError(response, errorFunc)

        if (data != null)
            return mapData(data)
        else
            throw NetworkException(statusMessage = "Generated Network Error Something went wrong")
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

    private fun mapData(source: RESPONSE): MODEL = mapper.mapFrom(source)
}

class ConfigsRemoteProvider(
    mapper: Mapper<ConfigResponse, ConfigModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<ConfigResponse, ConfigModel>(mapper) {

    override suspend fun getResponse(args: ArgumentsWrapper?): Response<ConfigResponse> =
        api.getConfigsAsync().await()
}

class PopularMoviesRemoteProvider(
    mapper: Mapper<MoviesResultResponse, MoviesResultModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<MoviesResultResponse, MoviesResultModel>(mapper) {

    override suspend fun getResponse(args: ArgumentsWrapper?): Response<MoviesResultResponse> =
        api.getPopularMoviesAsync().await()
}

class MoviesDetailsProvider(
    mapper: Mapper<MovieDetailsResponse, MovieDetailsModel>,
    private val api: ApiInterface
) : BaseRemoteProvider<MovieDetailsResponse, MovieDetailsModel>(mapper),
    NetworkProvider<MovieDetailsModel> {

    override suspend fun getResponse(args: ArgumentsWrapper?): Response<MovieDetailsResponse> {
        val movieId = if (args is MovieId) args.movieId
        else throw IllegalArgumentException("Invalid object type received")

        return api.searchByIdAsync(movieId).await()
    }
}