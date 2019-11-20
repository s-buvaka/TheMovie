package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.database.models.PopularMoviesModel
import com.wispapp.themovie.core.model.network.MovieId
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.models.NetworkException

interface DataSource<T> {

    suspend fun get(
        args: ArgumentsWrapper? = null,
        errorFunc: (exception: NetworkException) -> Unit
    ): T
}

abstract class BaseDataSource<T> : DataSource<T> {

    override suspend fun get(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): T {
        val id = getId(args)
        return when (val cacheState = getCachedState(id)) {
            is CacheState.Actual -> getCachedData(cacheState)
            is CacheState.Empty -> getFromRemote(args, errorFunc)
        }
    }

    abstract suspend fun getCachedState(id: Int = 0): CacheState<T>

    abstract fun getCachedData(cacheState: CacheState.Actual<T>): T

    abstract suspend fun getFromRemote(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): T

    abstract suspend fun putToCache(model: T)

    protected open fun getId(args: ArgumentsWrapper?): Int {
        return 0
    }
}

class ConfigsDataSource(
    private val networkProvider: NetworkProvider<ConfigModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<ConfigModel>
) : BaseDataSource<ConfigModel>() {

    override suspend fun getCachedState(id: Int): CacheState<ConfigModel> = cacheProvider.getById(0)

    override fun getCachedData(cacheState: CacheState.Actual<ConfigModel>): ConfigModel =
        cacheState.data

    override suspend fun getFromRemote(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): ConfigModel {
        val response = networkProvider.get(args, errorFunc)
        putToCache(response)
        return response
    }

    override suspend fun putToCache(model: ConfigModel) {
        cacheProvider.put(model)
    }
}

class PopularMoviesDataSource(
    private val networkProvider: NetworkProvider<MoviesResultModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<PopularMoviesModel>
) : BaseDataSource<List<PopularMoviesModel>>() {

    override suspend fun getCachedState(id: Int): CacheState<List<PopularMoviesModel>> =
        cacheProvider.getAll()

    override fun getCachedData(cacheState: CacheState.Actual<List<PopularMoviesModel>>): List<PopularMoviesModel> =
        cacheState.data

    override suspend fun getFromRemote(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<PopularMoviesModel> {
        val response = networkProvider.get(args, errorFunc).results
        putToCache(response)
        return response
    }

    override suspend fun putToCache(model: List<PopularMoviesModel>) {
        cacheProvider.putAll(model)
    }
}

class MovieDetailsDataSource(
    private val networkProvider: NetworkProvider<MovieDetailsModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<MovieDetailsModel>
) : BaseDataSource<MovieDetailsModel>() {

    override suspend fun getCachedState(id: Int): CacheState<MovieDetailsModel> =
        cacheProvider.getById(id)

    override fun getCachedData(cacheState: CacheState.Actual<MovieDetailsModel>): MovieDetailsModel =
        cacheState.data

    override suspend fun getFromRemote(
        args: ArgumentsWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): MovieDetailsModel {
        val response = networkProvider.get(args, errorFunc)
        putToCache(response)
        return response
    }

    override suspend fun putToCache(model: MovieDetailsModel) {
        cacheProvider.put(model)
    }

    override fun getId(args: ArgumentsWrapper?): Int {
        return if (args is MovieId)
            args.movieId
        else
            throw IllegalArgumentException("Invalid object type received")
    }

}

