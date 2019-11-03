package com.wispapp.themovie.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.MovieOverview
import com.wispapp.themovie.core.repository.MovieRepository
import kotlinx.coroutines.launch

class MoviesViewModel(private val movieRepo: MovieRepository) : BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverview>>()

    fun getPopularMovie() {
        foregroundScope.launch {
            val movies = movieRepo.getPopularMovie().toMutableList()
            popularMovieLiveData.postValue(movies)
        }
    }
}