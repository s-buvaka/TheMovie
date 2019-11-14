package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.network.MovieId
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val popularMovieDataSource: DataSource<MovieOverviewModel>,
    private val movieDetailsDataSource: DataSource<MovieDetailsModel>
) : BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverviewModel>>()
    val movieDetailsLiveData = MutableLiveData<MutableList<MovieDetailsModel>>()

    fun getPopularMovie() {
        backgroundScope.launch {
            val popularMovies =
                popularMovieDataSource.get(
                    errorFunc = { error -> handleError(error) }
                ).toMutableList()
            popularMovieLiveData.postValue(popularMovies)
        }
    }

    fun getMovieDetails(id: Int) {
        backgroundScope.launch {
            val movieDetails =
                movieDetailsDataSource.get(
                    args = MovieId(id),
                    errorFunc = { error -> handleError(error) }
                ).toMutableList()
            movieDetailsLiveData.postValue(movieDetails)
        }
    }

    private fun handleError(error: NetworkException) {
        Log.d(TAG, error.statusMessage)
    }
}