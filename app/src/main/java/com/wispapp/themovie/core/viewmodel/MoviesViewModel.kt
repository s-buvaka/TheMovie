package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.CATEGORY.*
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.MovieId
import com.wispapp.themovie.core.model.network.models.NetworkException
import com.wispapp.themovie.ui.viewholders.Categories
import com.wispapp.themovie.ui.viewholders.MovieCategory
import com.wispapp.themovie.ui.viewholders.MovieList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MoviesViewModel"

class MoviesViewModel(
    private val movieDataSource: DataSource<List<MovieModel>>,
    private val movieDetailsDataSource: DataSource<MovieDetailsModel>,
    private val configDataSource: DataSource<ConfigModel>,
    private val imageLoader: ImageLoader
) : BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<Categories>>()
    val movieDetailsLiveData = MutableLiveData<MutableList<MovieDetailsModel>>()

    fun getMovies() {
        showLoader()
        backgroundScope.launch {
            withContext(Dispatchers.IO) { getConfigs() }
            withContext(Dispatchers.IO) { getAllMovies() }

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

    private suspend fun getAllMovies() {
        when (val result = movieDataSource.get()) {
            is Result.Success -> updateUi(result)
            is Result.Error -> handleError(result.exception)
        }
    }

    private fun updateUi(result: Result.Success<List<MovieModel>>) {
        val nowPlaying = filterByCategory(result, NOW_PLAYING)
        val popular = filterByCategory(result, POPULAR)
        val topRated = filterByCategory(result, TOP_RATED)
        val upcoming = filterByCategory(result, UPCOMING)

        val movies = createCategoryList(nowPlaying, popular, topRated, upcoming)
        popularMovieLiveData.postValue(movies)
    }

    private fun filterByCategory(result: Result.Success<List<MovieModel>>, category: CATEGORY) =
        result.data.filter { it.category.contains(category) }

    private fun createCategoryList(
        nowPlaying: List<MovieModel>,
        popular: List<MovieModel>,
        topRated: List<MovieModel>,
        upcoming: List<MovieModel>
    ): MutableList<Categories> =
        mutableListOf(
            MovieCategory(NOW_PLAYING),
            MovieList(nowPlaying),
            MovieCategory(POPULAR),
            MovieList(popular),
            MovieCategory(TOP_RATED),
            MovieList(topRated),
            MovieCategory(UPCOMING),
            MovieList(upcoming)
        )

    private fun handleError(error: Exception) {
        if (error is NetworkException) {
            showError(error.statusMessage)
            Log.d(TAG, error.statusMessage)
        } else
            showError(error.message ?: "Something was wrong")
    }
}