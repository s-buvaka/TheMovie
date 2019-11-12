package com.wispapp.themovie.core.model.cache

interface CacheProvider<T> {
    suspend fun get(): CacheState<T>
    suspend fun put(data: List<T>)
}

sealed class CacheState<T> {
    data class Actual<T>(val data: List<T>) : CacheState<T>()
    class Empty<T> : CacheState<T>()
}