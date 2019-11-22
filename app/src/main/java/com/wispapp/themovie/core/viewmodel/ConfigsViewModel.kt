package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch

private const val TAG = "ConfigsViewModel"

class ConfigsViewModel(private val dataSource: DataSource<ConfigModel>) : BaseViewModel() {

    val configLiveData = MutableLiveData<ConfigModel>()

    fun getConfigs() {
        backgroundScope.launch {
            when (val result = dataSource.get()) {
                is Result.Success -> configLiveData.postValue(result.data)
                is Result.Error -> handleError(result.exception)
            }
        }
    }

    private fun handleError(error: Exception) {
        if (error is NetworkException) Log.d(TAG, error.statusMessage)
    }
}