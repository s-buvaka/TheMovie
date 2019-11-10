package com.wispapp.themovie.core.model.repository

import com.wispapp.themovie.core.model.network.models.configs.ConfigResponse

interface AppDataRepository {

    suspend fun getConfigs(): ConfigResponse?

    suspend fun loadAppData(): Boolean
}