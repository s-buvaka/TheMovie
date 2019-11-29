package com.wispapp.themovie.ui.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

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
    val exception = MutableLiveData<ErrorWrapper>()

    protected fun showLoader() {
        uiScope.launch { setLoading(true) }
    }

    protected fun hideLoader() {
        uiScope.launch { setLoading(false) }
    }

    protected fun showError(errorMessage: String, func: () -> Unit) {
        val errorWrapper = ErrorWrapper(errorMessage, func)
        uiScope.launch { setError(errorWrapper) }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        backgroundScope.cancel()
        uiScope.cancel()
    }

    private fun setLoading(isLoading: Boolean? = true) {
        isDataLoading.value = isLoading

        if (isLoading == true) {
            exception.value = null
        }
    }

    private fun setError(errorMessage: ErrorWrapper) {
        exception.value = errorMessage
    }

    fun cancelAllRequest() = parentJob.cancel()
}

data class ErrorWrapper(val errorMessage: String, val func: () -> Unit)