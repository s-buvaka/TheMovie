package com.wispapp.themovie.core.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    private val parentJob by lazy { Job() }

    protected val uiScope: CoroutineScope by lazy { CoroutineScope(parentJob + Dispatchers.Main) }
    protected val backgroundScope: CoroutineScope by lazy { CoroutineScope(parentJob + Dispatchers.IO) }

    /**
     * Handle data loading
     */
    val isDataLoading = MutableLiveData<Boolean>()

    /**
     * Handle errors
     */
    val exception = MutableLiveData<Throwable>()

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        backgroundScope.cancel()
        uiScope.cancel()
    }

    open fun setLoading(isLoading: Boolean? = true) {
        isDataLoading.value = isLoading

        if (isLoading == true) {
            exception.value = null
        }
    }

    open fun setError(t: Throwable) {
        exception.value = t
    }

    fun cancelAllRequest() = parentJob.cancel()
}