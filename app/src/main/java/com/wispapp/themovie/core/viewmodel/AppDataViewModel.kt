package com.wispapp.themovie.core.viewmodel

import androidx.lifecycle.MutableLiveData
import com.wispapp.themovie.core.network.model.configs.ConfigResponse
import com.wispapp.themovie.core.repository.AppDataRepository
import kotlinx.coroutines.launch

class AppDataViewModel(private val appRepo: AppDataRepository) : BaseViewModel() {

    val appDataLoaderLiveData = MutableLiveData<Boolean>()
    val configLiveData = MutableLiveData<ConfigResponse>()

    fun loadAppData() {
        foregroundScope.launch {
            val isDataLoaded = appRepo.loadAppData()
            appDataLoaderLiveData.postValue(isDataLoaded)
        }
    }

    fun getConfigs() {
        foregroundScope.launch {
            val configs = appRepo.getConfigs()
            configLiveData.postValue(configs)
        }
    }
}