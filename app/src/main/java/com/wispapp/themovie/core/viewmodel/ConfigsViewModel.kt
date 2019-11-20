package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.network.models.NetworkException
import kotlinx.coroutines.launch

private const val TAG = "ConfigsViewModel"

class ConfigsViewModel(private val dataSource: DataSource<ConfigModel>) : BaseViewModel() {

    val configLiveData = MutableLiveData<ConfigModel>()

    fun getConfigs() {
        backgroundScope.launch {
            val configs = dataSource.get(
                errorFunc = { error -> handleError(error) })

            configLiveData.postValue(configs)
        }
    }

    private fun handleError(error: NetworkException) {
        Log.d(TAG, error.statusMessage)
    }
}