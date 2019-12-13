package com.wispapp.themovie.ui.base

interface BaseView {

    fun showLoading()

    fun hideLoading()

    fun showError(errorMessage: String, repeatFunc: (() -> Unit)?)

    fun hideError()
}