package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.network.MovieId
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val popularMovieDataSource: DataSource<MovieOverviewModel>,
    private val movieDetailsDataSource: DataSource<MovieDetailsModel>,
    private val dataSource: DataSource<ConfigModel>,
    private val imageLoader: ImageLoader
) : BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverviewModel>>()
    val movieDetailsLiveData = MutableLiveData<MutableList<MovieDetailsModel>>()

    fun getPopularMovie() {
        showLoader()
        backgroundScope.launch {
            setImageConfigs()

            val popularMovies = withContext(Dispatchers.IO) { getPopularMovies() }
            popularMovieLiveData.postValue(popularMovies)

           hideLoader()
        }
    }

    fun getMovieDetails(id: Int) {
        showLoader()
        backgroundScope.launch {
            val movieDetails =
                movieDetailsDataSource.get(
                    args = MovieId(id),
                    errorFunc = { error -> handleError(error) }
                ).toMutableList()
            movieDetailsLiveData.postValue(movieDetails)

            hideLoader()
        }
    }

    private suspend fun setImageConfigs() {
        //Todo Задавать конфиги при старте приложение, может быть splash
        val configs = withContext(Dispatchers.IO) { getConfigs() }
        imageLoader.setConfigs(configs[0].imagesConfig)
    }

    private suspend fun getPopularMovies(): MutableList<MovieOverviewModel> {
        return popularMovieDataSource.get(
            errorFunc = { error -> handleError(error) }
        ).toMutableList()
    }

    private suspend fun getConfigs() =
        dataSource.get(
            errorFunc = { error -> Log.d(TAG, error.statusMessage) })

    private fun handleError(error: NetworkException) {
        Log.d(TAG, error.statusMessage)
    }
}