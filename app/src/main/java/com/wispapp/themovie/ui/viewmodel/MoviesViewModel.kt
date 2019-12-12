package com.wispapp.themovie.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.*
import com.wispapp.themovie.core.model.database.models.CATEGORY.*
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.MovieIdArgs
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewModel(
    private val movieDataSource: DataSource<List<MovieModel>>,
    private val movieDetailsDataSource: DataSource<MovieDetailsModel>,
    private val imagesDataSource: DataSource<ImagesResultModel>,
    private val trailersDataSource: DataSource<List<TrailerModel>>,
    private val reviewsDataSource: DataSource<List<ReviewModel>>
) : BaseViewModel() {

    private var isFirstLoading = true

    val nowPlayingMoviesLiveData = MutableLiveData<MutableList<MovieModel>>()
    val popularMoviesLiveData = MutableLiveData<MutableList<MovieModel>>()
    val topRatedMovieLiveData = MutableLiveData<MutableList<MovieModel>>()
    val upcomingMoviesLiveData = MutableLiveData<MutableList<MovieModel>>()
    val movieDetailsLiveData = MutableLiveData<MutableList<MovieDetailsModel>>()
    val imagesLiveData = MutableLiveData<MutableList<ImageModel>>()
    val selectedImageLiveData = MutableLiveData<MutableList<ImageModel>>()
    val trailersLiveData = MutableLiveData<MutableList<TrailerModel>>()
    val reviewsLiveData = MutableLiveData<MutableList<ReviewModel>>()
    val expandAnimationLiveData = MutableLiveData<MutableList<Boolean>>()

    fun getMovies() {
        if (isFirstLoading) showLoader()
        backgroundScope.launch {
            getAllMovies()
            hideLoader()
        }
    }

    fun getMovieDetails(id: Int) {
        showLoader()
        backgroundScope.launch {
            when (val result = movieDetailsDataSource.get(MovieIdArgs(id))) {
                is Result.Success -> movieDetailsLiveData.postValue(mutableListOf(result.data))
                is Result.Error -> handleError(result.exception) { getMovieDetails(id) }
            }
            hideLoader()
        }
    }

    fun getImages(id: Int) {
        backgroundScope.launch {
            when (val result = imagesDataSource.get(MovieIdArgs(id))) {
                is Result.Success -> imagesLiveData.postValue(mapImages(result.data))
                is Result.Error -> handleError(result.exception) { getImages(id) }
            }
        }
    }

    fun getTrailers(id: Int) {
        backgroundScope.launch {
            when (val result = trailersDataSource.get(MovieIdArgs(id))) {
                is Result.Success -> trailersLiveData.postValue(result.data.toMutableList())
                is Result.Error -> handleError(result.exception) { getTrailers(id) }
            }
        }
    }

    fun getReviews(id: Int) {
        backgroundScope.launch {
            when (val result = reviewsDataSource.get(MovieIdArgs(id))) {
                is Result.Success -> reviewsLiveData.postValue(result.data.toMutableList())
                is Result.Error -> handleError(result.exception) { getTrailers(id) }
            }
        }
    }

    private suspend fun getAllMovies() {
        when (val result = movieDataSource.get()) {
            is Result.Success -> updateUi(result)
            is Result.Error -> handleError(result.exception) { getMovies() }
        }
    }

    private fun updateUi(result: Result.Success<List<MovieModel>>) {
        val nowPlaying = filterByCategory(result, NOW_PLAYING)
        val popular = filterByCategory(result, POPULAR)
        val topRated = filterByCategory(result, TOP_RATED)
        val upcoming = filterByCategory(result, UPCOMING)

        nowPlayingMoviesLiveData.postValue(nowPlaying)
        popularMoviesLiveData.postValue(popular)
        topRatedMovieLiveData.postValue(topRated)
        upcomingMoviesLiveData.postValue(upcoming)

        expandMovieList()
    }

    private fun expandMovieList() {
        expandAnimationLiveData.postValue(mutableListOf(isFirstLoading))
        isFirstLoading = false
    }

    private fun filterByCategory(result: Result.Success<List<MovieModel>>, category: CATEGORY) =
        result.data.filter { it.category.contains(category) }.toMutableList()

    private fun mapImages(imageModel: ImagesResultModel): MutableList<ImageModel>? =
        mutableListOf<ImageModel>().apply {
            addAll(imageModel.backdrops)
            addAll(imageModel.posters)
        }

    private fun handleError(error: Exception, func: () -> Unit) {
        if (error is NetworkException) {
            showError(error.statusMessage, func)
            Timber.d(error.statusMessage)
        } else
            showError(error.message ?: "Movie View Model: Error receiving data", func)
    }
}