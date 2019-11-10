package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.DataSource
import kotlinx.coroutines.launch

class MoviesViewModel(private val dataSource: DataSource<MovieOverviewModel>) :
    BaseViewModel() {

    val popularMovieLiveData = MutableLiveData<MutableList<MovieOverviewModel>>()

    fun getPopularMovie() {
        foregroundScope.launch {
            val movies = dataSource.get(
                errorFunc = { error -> Log.d("XXX", error.statusMessage) })
                .toMutableList()
            popularMovieLiveData.postValue(movies)
        }
    }
}