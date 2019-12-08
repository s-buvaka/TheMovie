package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.database.models.ReviewModel
import com.wispapp.themovie.core.model.database.models.ReviewResultModel
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.NetworkProvider

class ReviewsDataSource(private val networkProvider: NetworkProvider<ReviewResultModel>) :
    BaseDataSource<List<ReviewModel>>() {

    override suspend fun getCachedState(id: Int): CacheState<List<ReviewModel>> =
        CacheState.Empty()

    override suspend fun getFromRemote(args: ArgumentsWrapper?): Result<List<ReviewModel>> {
        return when (val response = networkProvider.get(args)) {
            is Result.Success -> return Result.Success(response.data.results)
            is Result.Error -> return response
            else -> Result.Error(Exception("Data Source: Error receiving data"))
        }
    }
}