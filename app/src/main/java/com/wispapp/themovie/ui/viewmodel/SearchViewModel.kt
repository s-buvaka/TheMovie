package com.wispapp.themovie.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.SearchQueryArgs
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch
import timber.log.Timber

class SearchViewModel(private val searchMovieDataSource: DataSource<List<MovieModel>>) :
    BaseViewModel() {

    val searchMovieLiveData = MutableLiveData<MutableList<MovieModel>>()

    fun searchMovie(query: String) {
        backgroundScope.launch {
            when (val result = searchMovieDataSource.get((SearchQueryArgs(query)))) {
                is Result.Success -> searchMovieLiveData.postValue(result.data.toMutableList())
                is Result.Error -> handleError(result.exception) { searchMovie(query) }
            }
        }
    }

    private fun handleError(error: Exception, func: () -> Unit) {
        if (error is NetworkException) {
            showError(error.statusMessage, func)
            Timber.d(error.statusMessage)
        } else
            showError(error.message ?: "Search View Model: Error receiving data", func)
    }
}