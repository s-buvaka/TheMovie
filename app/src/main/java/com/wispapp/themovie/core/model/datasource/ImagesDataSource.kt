package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.database.models.ImagesResultModel
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.MovieIdArgs
import com.wispapp.themovie.core.model.network.NetworkProvider

class ImagesDataSource(
    private val networkProvider: NetworkProvider<ImagesResultModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<ImagesResultModel>
) : BaseDataSource<ImagesResultModel>() {

    override suspend fun getCachedState(id: Int): CacheState<ImagesResultModel> = cacheProvider.getById(id)

    override fun getCachedData(cacheState: CacheState.Actual<ImagesResultModel>): Result<ImagesResultModel> =
        Result.Success(cacheState.data)

    override suspend fun getFromRemote(args: ArgumentsWrapper?): Result<ImagesResultModel> {
        return when (val response = networkProvider.get(args)) {
            is Result.Success -> {
                putToCache(response.data)
                return response
            }
            is Result.Error -> return response
            else -> Result.Error(Exception("Data Source: Error receiving data"))
        }
    }

    override suspend fun putToCache(model: ImagesResultModel) {
        cacheProvider.put(model)
    }

    override fun getId(args: ArgumentsWrapper?): Int {
        return if (args is MovieIdArgs)
            args.movieId
        else
            throw IllegalArgumentException("Invalid object type received")
    }
}