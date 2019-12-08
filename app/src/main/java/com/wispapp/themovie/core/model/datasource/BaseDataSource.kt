package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.network.ArgumentsWrapper

interface DataSource<T> {

    suspend fun get(args: ArgumentsWrapper? = null): Result<T>
}

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
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

    abstract suspend fun getFromRemote(args: ArgumentsWrapper?): Result<T>

    protected open fun getCachedData(cacheState: CacheState.Actual<T>): Result<T> =
        Result.Error(Exception("Not implemented method [getCachedData]"))

    protected open suspend fun putToCache(model: T) {}

    protected open fun getId(args: ArgumentsWrapper?): Int = 0
}
