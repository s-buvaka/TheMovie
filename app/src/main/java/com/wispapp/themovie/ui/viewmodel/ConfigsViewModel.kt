package com.wispapp.themovie.ui.viewmodel

import com.wispapp.themovie.core.common.ConfigsHolder
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.Result
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch
import timber.log.Timber

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
        if (error is NetworkException) Timber.d(error.statusMessage)
    }
}