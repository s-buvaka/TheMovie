package com.wispapp.themovie.core.model.cache

interface CacheProvider<T> {

    suspend fun getAll(): CacheState<T>

    suspend fun getById(id: Int): CacheState<T>

    suspend fun put(data: List<T>)
}

sealed class CacheState<T> {
    data class AllObjects<T>(val data: List<T>) : CacheState<T>()
    data class Object<T>(val data: T) : CacheState<T>()
    class Empty<T> : CacheState<T>()
}