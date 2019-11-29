package com.wispapp.themovie.ui.viewmodel

import android.util.Log
import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch

private const val TAG = "ConfigsViewModel"

class ConfigsViewModel(private val dataSource: DataSource<ConfigModel>) : BaseViewModel() {

    fun loadConfigs() {
        backgroundScope.launch {
            when (val result = dataSource.get()) {
                is Result.Success -> ConfigsHolder.setConfigs(result.data.imagesConfig)
                is Result.Error -> handleError(result.exception)
            }
        }
    }

    private fun handleError(error: Exception) {
        if (error is NetworkException) Log.d(TAG, error.statusMessage)
    }
}