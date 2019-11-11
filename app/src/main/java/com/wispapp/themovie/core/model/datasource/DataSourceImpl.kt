package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.database.SourceDatabase
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
    private val database: SourceDatabase<DATA>
) : DataSource<DATA> {

    override suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        vararg: RequestWrapper?
    ): List<DATA> {
        val data = database.getAll()
        val response = networkProvider.get(errorFunc, vararg)

        return if (data.isNotEmpty()) data
        else response
    }
}