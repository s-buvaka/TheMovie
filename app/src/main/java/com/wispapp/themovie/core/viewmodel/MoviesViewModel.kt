package com.wispapp.themovie.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.network.PopularMoviesProvider
import kotlinx.coroutines.launch

class MoviesViewModel(/*private val movieRepo: MovieRepository*/ private val networkProvider: PopularMoviesProvider) :
    BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverviewModel>>()

    fun getPopularMovie() {
        foregroundScope.launch {
            val movies = networkProvider.getPopularMovies()?.results?.toMutableList()
            //val movies = movieRepo.getPopularMovie().toMutableList()
            popularMovieLiveData.postValue(movies)
        }
    }
}