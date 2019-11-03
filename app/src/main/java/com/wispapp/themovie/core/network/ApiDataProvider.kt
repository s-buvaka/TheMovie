package com.wispapp.themovie.core.network

import com.wispapp.themovie.R
import com.wispapp.themovie.core.application.App

private const val BASE_URL = "https://api.themoviedb.org/3/"

interface ApiDataProvider {

    fun provideBaseUrl(): String

    fun provideApiKey(): String
}

class ApiDataProviderImpl : ApiDataProvider {

    override fun provideBaseUrl(): String = BASE_URL

    override fun provideApiKey(): String =
        App.applicationContext().getString(R.string.api_key)
}