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

    override suspend fun getAll(): CacheState<List<T>> = getCachedData()

    override suspend fun getById(id: Int): CacheState<T> = getCachedData(id)

    override suspend fun putAll(data: List<T>) {
        clearData()
        saveData(data)
        innerSaveTimeStamp()
    }

    override suspend fun put(data: T) {
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

    private fun getCachedData(): CacheState<List<T>> {
        return when {
            timeoutPolicy.isValid() -> getFromDb()
            else -> emptyState()
        }
    }

    private fun getFromDb(): CacheState<List<T>> {
        val cachedData = database.getAll()
        return CacheState.Actual(cachedData)
    }

    private fun emptyState(): CacheState<List<T>> {
        clearData()
        return CacheState.Empty()
    }

    private fun getCachedData(id: Int): CacheState<T> {
        return when {
            timeoutPolicy.isValid() -> getFromDb(id)
            else -> CacheState.Empty()
        }
    }

    private fun getFromDb(id: Int): CacheState<T> {
        val cachedData = database.getById(id)
        return when {
            cachedData != null -> CacheState.Actual(cachedData)
            else -> CacheState.Empty()
        }
    }

    private fun clearData() {
        database.delTimestamp(type.name)
        database.deleteAll()
    }

    private fun saveData(data: List<T>) {
        database.insertAll(data)
    }

    private fun saveData(data: T) {
        database.insert(data)
    }

    private fun updateData(data: T) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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