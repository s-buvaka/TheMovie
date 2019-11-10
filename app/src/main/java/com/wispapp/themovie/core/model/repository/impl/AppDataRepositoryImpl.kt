package com.wispapp.themovie.core.model.repository.impl

//class AppDataRepositoryImpl<DATA>(
//    private val api: ApiInterface,
//    private val database: SourceDatabase<DATA>
//) : BaseRepository(), AppDataRepository {
//
//    override suspend fun loadAppData(): Boolean {
//        val configResponse = parseResponse(
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
//        parseResponse(
//            call = { api.getConfigsAsync().await() },
//            errorMessage = ""
//        )
//}