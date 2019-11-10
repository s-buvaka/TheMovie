package com.wispapp.themovie.core.repository.impl

//class MovieRepositoryImpl(
//    private val api: ApiInterface,
//    mapper: Mapper<MovieOverviewResponse, MovieOverviewDao>
//) : BaseRepository<MovieOverviewResponse, MovieOverviewDao>(mapper), MovieRepository {
//
//    override suspend fun getPopularMovie(): List<MovieOverviewResponse> {
//        val popularMovie = safeApiCall(
//            call = { api.getPopularMoviesAsync().await() },
//            errorMessage = {}
//        )
//        return popularMovie?.results ?: emptyList()
//    }
//}