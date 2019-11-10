package com.wispapp.themovie.core.model.datasource

import com.wispapp.themovie.core.model.network.RequestWrapper
import com.wispapp.themovie.core.model.network.models.NetworkException

interface DataSource<T> {

    suspend fun get(
        errorFunc: (exception: NetworkException) -> Unit,
        vararg: RequestWrapper? = null
    ): List<T>
}