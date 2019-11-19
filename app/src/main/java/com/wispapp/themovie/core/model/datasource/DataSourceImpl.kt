package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.RequestWrapper
import com.wispapp.themovie.core.model.network.models.NetworkException

class RemoteDataSourceImpl<DATA>(private val networkProvider: NetworkProvider<DATA>) :
    DataSource<DATA> {

    override suspend fun get(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<DATA> {
        return networkProvider.get(args, errorFunc)
    }
}

class CachedDataSourceImpl<DATA>(
    private val networkProvider: NetworkProvider<DATA>,
    private val cacheProvider: DataBaseSourceCacheProvider<DATA>
) : DataSource<DATA> {

    override suspend fun get(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<DATA> {

        return when (val cacheState = cacheProvider.getAll()) {
            is CacheState.AllObjects -> getCachedData(cacheState)
            //is CacheState.Object -> getCachedData(cacheState)
            is CacheState.Empty -> getFromRemote(args, errorFunc)
        }
    }

    private fun getCachedData(cacheState: CacheState.AllObjects<DATA>) =
        cacheState.data

    private fun getCachedData(cacheState: CacheState.Object<DATA>) =
        cacheState.data

    private suspend fun getFromRemote(
        args: RequestWrapper?,
        errorFunc: (exception: NetworkException) -> Unit
    ): List<DATA> {
        val response = networkProvider.get(args, errorFunc)
        putToCache(response)
        return response
    }

    private suspend fun putToCache(response: List<DATA>) {
        cacheProvider.put(response)
    }
}