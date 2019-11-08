package com.wispapp.themovie.core.repository.impl

import com.wispapp.themovie.core.network.ApiInterface
import com.wispapp.themovie.core.network.model.configs.ConfigResponse
import com.wispapp.themovie.core.repository.AppDataRepository
import com.wispapp.themovie.core.repository.BaseRepository

//class AppDataRepositoryImpl<DATA>(
//    private val api: ApiInterface,
//    private val database: SourceDao<DATA>
//) : BaseRepository(), AppDataRepository {
//
//    override suspend fun loadAppData(): Boolean {
//        val configResponse = safeApiCall(
//            call = { api.getConfigsAsync().await() },
//            errorMessage = ""
//        )
//
//        database.insert(configResponse)
//
//        return configResponse != null
//    }
//
//    override suspend fun getConfigs(): ConfigResponse? =
//        safeApiCall(
//            call = { api.getConfigsAsync().await() },
//            errorMessage = ""
//        )
//}