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
        vararg: RequestWrapper?
    ): List<DATA> {
        return networkProvider.get(errorFunc, vararg)
    }
}

class CachedDataSourceImpl<DATA>(
    private val networkProvider: NetworkProvider<DATA>,
    private val cacheProvider: DataBaseSourceCacheProvider<DATA>
) : DataSource<DATA> {

    override suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        vararg: RequestWrapper?
    ): List<DATA> {
        val data: List<DATA>
        val cacheState = cacheProvider.get()

        when(cacheState){
            is CacheState.Actual ->  data = cacheState.data
            is CacheState.Empty-> data = emptyList()
        }
        val response = networkProvider.get(errorFunc, vararg)
       // cacheProvider.insertAll(response)

        return if (data.isNotEmpty()) data
        else response
    }

//    private fun getData(): List<DATA>  {
//
//    }
}