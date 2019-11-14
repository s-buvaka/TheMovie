package com.wispapp.themovie.core.model.cache

import com.wispapp.themovie.core.model.database.SourceDatabase
import com.wispapp.themovie.core.model.database.models.SourceType
import com.wispapp.themovie.core.model.database.models.SourcesTimeStamp
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DataBaseSourceCacheProvider<T>(
    private val timeoutPolicy: TimeoutCachePolicy,
    private val type: SourceType,
    private val database: SourceDatabase<T>
) : CacheProvider<T> {

    init {
        checkTimeStamp()
    }

    override suspend fun get(): CacheState<T> = getCachedData()

    override suspend fun put(data: List<T>) {
        clearData()
        saveData(data)
        innerSaveTimeStamp()
    }

    private fun checkTimeStamp() {
        GlobalScope.launch {
            database.getTimestamp(type.name)?.let {
            timeoutPolicy.setTimeStamp(it)
            }
        }
    }

    private fun getCachedData(): CacheState<T> {
        return when {
            timeoutPolicy.isValid() -> getFromDb()
            else -> returnEmptyState()
        }
    }

    private fun getFromDb(): CacheState<T> {
        val cacheData = database.getAll()
        return CacheState.Actual(cacheData)
    }

    private fun returnEmptyState(): CacheState<T> {
        clearData()
        return CacheState.Empty()
    }

    private fun clearData() {
        database.delTimestamp(type.name)
        database.deleteAll()
    }

    private fun saveData(data: List<T>) {
        database.insertAll(data)
    }

    private fun innerSaveTimeStamp() {
        timeoutPolicy.setTimeStamp(System.currentTimeMillis())
        database.setTimestamp(
            SourcesTimeStamp(
                timeStamp = timeoutPolicy.getTimeStamp(),
                type = type.name
            )
        )
    }
}