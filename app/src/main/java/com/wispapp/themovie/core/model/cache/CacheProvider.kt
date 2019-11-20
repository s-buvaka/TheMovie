package com.wispapp.themovie.core.model.cache

interface CacheProvider<T> {

    suspend fun getAll(): CacheState<List<T>>

    suspend fun getById(id: Int): CacheState<T>

    suspend fun putAll(data: List<T>)

    suspend fun put(data: T)
}

sealed class CacheState<T> {
    data class Actual<T>(val data: T) : CacheState<T>()
    class Empty<T> : CacheState<T>()
}