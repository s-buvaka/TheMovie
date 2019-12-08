package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.database.models.MovieTrailersModel
import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.NetworkProvider

class MovieTrailersDataSource(private val networkProvider: NetworkProvider<MovieTrailersModel>) :
    BaseDataSource<List<TrailerModel>>() {

    override suspend fun getCachedState(id: Int): CacheState<List<TrailerModel>> =
        CacheState.Empty()

    override suspend fun getFromRemote(args: ArgumentsWrapper?): Result<List<TrailerModel>> {
        return when (val response = networkProvider.get(args)) {
            is Result.Success -> return Result.Success(response.data.results)
            is Result.Error -> return response
            else -> Result.Error(Exception("Something was wrong"))
        }
    }
}