package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.network.ArgumentsWrapper

interface DataSource<T> {

    suspend fun get(args: ArgumentsWrapper? = null): Result<T>
}

abstract class BaseDataSource<T> : DataSource<T> {

    override suspend fun get(args: ArgumentsWrapper?): Result<T> {
        val id = getId(args)
        return when (val cacheState = getCachedState(id)) {
            is CacheState.Actual -> getCachedData(cacheState)
            is CacheState.Empty -> getFromRemote(args)
        }
    }

    abstract suspend fun getCachedState(id: Int = 0): CacheState<T>

    abstract fun getCachedData(cacheState: CacheState.Actual<T>): Result<T>

    abstract suspend fun getFromRemote(args: ArgumentsWrapper?): Result<T>

    abstract suspend fun putToCache(model: T)

    protected open fun getId(args: ArgumentsWrapper?): Int = 0
}
