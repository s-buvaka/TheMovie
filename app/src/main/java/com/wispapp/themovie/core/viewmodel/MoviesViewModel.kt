package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.PopularMoviesModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.network.MovieId
import com.wispapp.themovie.core.model.network.Result
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val popularMovieDataSource: DataSource<List<PopularMoviesModel>>,
    private val movieDetailsDataSource: DataSource<MovieDetailsModel>,
    private val configDataSource: DataSource<ConfigModel>,
    private val imageLoader: ImageLoader
) : BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<PopularMoviesModel>>()
    val movieDetailsLiveData = MutableLiveData<MutableList<MovieDetailsModel>>()

    fun getPopularMovie() {
        showLoader()
        backgroundScope.launch {

            withContext(Dispatchers.IO) { getConfigs() }
            withContext(Dispatchers.IO) { getPopularMovies() }

            hideLoader()
        }
    }

    fun getMovieDetails(id: Int) {
        showLoader()
        backgroundScope.launch {
            when (val result = movieDetailsDataSource.get(MovieId(id))) {
                is Result.Success -> movieDetailsLiveData.postValue(mutableListOf(result.data))
                is Result.Error -> handleError(result.exception)
            }

            hideLoader()
        }
    }

    private suspend fun getConfigs() {
        when (val result = configDataSource.get()) {
            is Result.Success -> imageLoader.setConfigs(result.data.imagesConfig)
            is Result.Error -> handleError(result.exception)
        }
    }

    private suspend fun getPopularMovies() {
        when (val result = popularMovieDataSource.get()) {
            is Result.Success -> popularMovieLiveData.postValue(result.data.toMutableList())
            is Result.Error -> handleError(result.exception)
        }
    }

    private fun handleError(error: Exception) {
        if (error is NetworkException) {
            showError(error.statusMessage)
            Log.d(TAG, error.statusMessage)
        } else
            showError(error.message ?: "Something was wrong")
    }
}