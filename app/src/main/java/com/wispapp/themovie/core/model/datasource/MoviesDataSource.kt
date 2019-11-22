package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.cache.CacheState
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.network.ArgumentsWrapper
import com.wispapp.themovie.core.model.network.NetworkProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesDataSource(
    private val nowPlayingNetworkProvider: NetworkProvider<MoviesResultModel>,
    private val popularsNetworkProvider: NetworkProvider<MoviesResultModel>,
    private val topRatedNetworkProvider: NetworkProvider<MoviesResultModel>,
    private val upcomingProvider: NetworkProvider<MoviesResultModel>,
    private val cacheProvider: DataBaseSourceCacheProvider<MovieModel>
) : BaseDataSource<List<MovieModel>>() {

    override suspend fun getCachedState(id: Int): CacheState<List<MovieModel>> =
        cacheProvider.getAll()

    override fun getCachedData(cacheState: CacheState.Actual<List<MovieModel>>): Result<List<MovieModel>> =
        Result.Success(cacheState.data)

    override suspend fun getFromRemote(args: ArgumentsWrapper?): Result<List<MovieModel>> {
        var allMovies = emptyList<MovieModel>()

        val nowPlayingMovies = withContext(Dispatchers.IO) { getData(args, nowPlayingNetworkProvider) }
        val popularMovies = withContext(Dispatchers.IO) { getData(args, popularsNetworkProvider) }
        val topRatedMovies = withContext(Dispatchers.IO) { getData(args, topRatedNetworkProvider) }
        val upcomingMovies = withContext(Dispatchers.IO) { getData(args, upcomingProvider) }

        if (nowPlayingMovies is Result.Success)
            allMovies = mergeMovies(allMovies, nowPlayingMovies.data.toMutableList())

        if (popularMovies is Result.Success)
            allMovies = mergeMovies(allMovies, popularMovies.data.toMutableList())

        if (topRatedMovies is Result.Success)
            allMovies = mergeMovies(allMovies, topRatedMovies.data.toMutableList())

        if (upcomingMovies is Result.Success)
            allMovies = mergeMovies(allMovies, upcomingMovies.data.toMutableList())

        val isAllDataEmpty = nowPlayingMovies is Result.Error &&
                popularMovies is Result.Error &&
                topRatedMovies is Result.Error &&
                upcomingMovies is Result.Error

        return if (isAllDataEmpty)
            Result.Error(Exception("Remote data is empty"))
        else {
            putToCache(allMovies)
            Result.Success(allMovies)
        }
    }

    override suspend fun putToCache(model: List<MovieModel>) =
        cacheProvider.putAll(model)

    private suspend fun getData(
        args: ArgumentsWrapper?,
        networkProvider: NetworkProvider<MoviesResultModel>
    ): Result<List<MovieModel>> {
        return when (val response = networkProvider.get(args)) {
            is Result.Success -> {
                val result = response.data.results
                return Result.Success(result)
            }
            is Result.Error -> return response
            else -> Result.Error(Exception("Something was wrong"))
        }
    }

    private fun mergeMovies(oldData: List<MovieModel>, newData: MutableList<MovieModel>): List<MovieModel> {
        val updatedData = oldData.toMutableList()

        oldData.forEach { oldMovie ->
            newData.find { newMovie -> newMovie.id == oldMovie.id }?.let {
                it.category.addAll(oldMovie.category)
                updatedData.remove(it)
                updatedData.add(it)
                newData.remove(it)
            }
        }

        updatedData.addAll(newData)
        return updatedData
    }

}