package com.wispapp.themovie.core.network

const val BASE_URL = "https://api.themoviedb.org/3/"

interface BaseUrlProvider {
    fun provide(): String
}

class BaseUrlProviderImpl : BaseUrlProvider {
    override fun provide(): String {
        return BASE_URL
    }
}