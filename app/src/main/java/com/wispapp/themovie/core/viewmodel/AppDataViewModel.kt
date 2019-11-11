package com.wispapp.themovie.core.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.datasource.DataSource
import kotlinx.coroutines.launch

class AppDataViewModel(private val dataSource: DataSource<ConfigModel>) : BaseViewModel() {

    val appDataLoaderLiveData = MutableLiveData<Boolean>()
    val configLiveData = MutableLiveData<ConfigModel>()

    fun loadAppData() {
        foregroundScope.launch {
            dataSource.get(
                errorFunc = { error -> Log.d("XXX", error.statusMessage) })
            appDataLoaderLiveData.postValue(true)
        }
    }

    fun getConfigs() {
        foregroundScope.launch {
            val configs = dataSource.get(
                errorFunc = { error -> Log.d("XXX", error.statusMessage) })

            configLiveData.postValue(configs[0])
        }
    }
}