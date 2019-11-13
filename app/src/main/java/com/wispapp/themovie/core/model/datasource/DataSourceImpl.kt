package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.RequestWrapper
import com.wispapp.themovie.core.model.network.models.NetworkException

class RemoteDataSourceImpl<DATA>(
    private val networkProvider: NetworkProvider<DATA>
) :
    DataSource<DATA> {

    override suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        args: RequestWrapper
    ): List<DATA> {
        return networkProvider.get(errorFunc, args)
    }
}

class CachedDataSourceImpl<DATA>(
    private val networkProvider: NetworkProvider<DATA>,
    private val cacheProvider: DataBaseSourceCacheProvider<DATA>
) : DataSource<DATA> {

    override suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        args: RequestWrapper
    ): List<DATA> {

        return when (val cacheState = cacheProvider.get()) {
            is CacheState.Actual -> getCachedData(cacheState)
            is CacheState.Empty -> getFromRemote(errorFunc, args)
        }
    }

    private fun getCachedData(cacheState: CacheState.Actual<DATA>) =
        cacheState.data

    private suspend fun getFromRemote(
        errorFunc: (exception: NetworkException) -> Unit,
        args: RequestWrapper
    ) = networkProvider.get(errorFunc, args)
}