package com.wispapp.themovie.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.database.model.MovieOverviewDao
import com.wispapp.themovie.core.network.PopularMoviesProvider
import kotlinx.coroutines.launch

class MoviesViewModel(/*private val movieRepo: MovieRepository*/ private val networkProvider: PopularMoviesProvider) :
    BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverviewDao>>()

    fun getPopularMovie() {
        foregroundScope.launch {
            val movies = networkProvider.getPopularMovies()?.results?.toMutableList()
            //val movies = movieRepo.getPopularMovie().toMutableList()
            popularMovieLiveData.postValue(movies)
        }
    }
}