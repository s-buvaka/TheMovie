package com.wispapp.themovie.core.repository

import com.wispapp.themovie.core.network.model.configs.ConfigResponse

interface AppDataRepository {

    suspend fun getConfigs(): ConfigResponse?

    suspend fun loadAppData(): Boolean
}