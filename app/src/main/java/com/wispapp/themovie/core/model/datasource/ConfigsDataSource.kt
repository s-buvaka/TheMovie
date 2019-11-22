package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.NetworkProvider

class ConfigsDataSource(
    private val networkProvider: NetworkProvider<ConfigModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<ConfigModel>
) : BaseDataSource<ConfigModel>() {

    override suspend fun getCachedState(id: Int): CacheState<ConfigModel> = cacheProvider.getById(0)

    override fun getCachedData(cacheState: CacheState.Actual<ConfigModel>): Result<ConfigModel> =
        Result.Success(cacheState.data)

    override suspend fun getFromRemote(args: ArgumentsWrapper?): Result<ConfigModel> {

        return when (val response = networkProvider.get(args)) {
            is Result.Success -> {
                putToCache(response.data)
                return response
            }
            is Result.Error -> return response
            else -> Result.Error(Exception("Something was wrong"))
        }
    }

    override suspend fun putToCache(model: ConfigModel) {
        cacheProvider.put(model)
    }
}