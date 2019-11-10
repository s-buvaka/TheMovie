package com.wispapp.themovie.core.model.repository.impl

//class MovieRepositoryImpl(
//    private val api: ApiInterface,
//    mapper: Mapper<MovieOverviewResponse, MovieOverviewModel>
//) : BaseRepository<MovieOverviewResponse, MovieOverviewModel>(mapper), MovieRepository {
//
//    override suspend fun getPopularMovie(): List<MovieOverviewResponse> {
//        val popularMovie = parseResponse(
//            call = { api.getPopularMoviesAsync().await() },
//            errorMessage = {}
//        )
//        return popularMovie?.results ?: emptyList()
//    }
//}