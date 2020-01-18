package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.database.models.MoviesResultModel
import com.wispapp.themovie.core.model.network.models.MovieResponse
import com.wispapp.themovie.core.model.network.models.MoviesResultResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class MoviesMapperTest {

    private lateinit var moviesMapper: MoviesMapper
    private lateinit var movieResultMapper: MovieResultMapper

    private lateinit var mockMovieResponse: MovieResponse
    private lateinit var mockMoviesResultResponse: MoviesResultResponse

    @Before
    fun setUp() {
        moviesMapper = MoviesMapper(getCategory())
        movieResultMapper = MovieResultMapper(moviesMapper)

        mockMovieResponse = MovieResponse(
            id = Random.nextInt(0, Int.MAX_VALUE),
            title = "Ad Astra",
            originalTitle = "Ad Astra",
            overview = "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond.",
            popularity = 478.246,
            originalLanguage = "en",
            hasVideo = true,
            genreIds = listOf(12, 18, 9648, 878, 53),
            posterPath = "/db32LaOibwEliAmSL2jjDF6oDdj.jpg",
            backdropPath = "/jOzrELAzFxtMx2I4uDGHOotdfsS.jpg",
            releaseDate = "2019-09-17",
            isAdult = false,
            voteAverage = 6.0,
            voteCount = 2498
        )

        mockMoviesResultResponse = MoviesResultResponse(
            page = 1,
            totalResults = 1039,
            totalPages = 52,
            results = listOf(mockMovieResponse, mockMovieResponse)
        )
    }

    @Test
    fun mapFrom() {
        val newMovieData = moviesMapper.mapFrom(mockMovieResponse)
        val newMovieResultData = movieResultMapper.mapFrom(mockMoviesResultResponse)

        testMovieMapper(newMovieData)
        testMovieResultMapper(newMovieResultData)
    }

    private fun testMovieMapper(newData: MovieModel) {
        assertEquals(mockMovieResponse.id, newData.id)
        assertEquals(mockMovieResponse.title, newData.title)
        assertEquals(mockMovieResponse.originalTitle, newData.originalTitle)
        assertEquals(mockMovieResponse.overview, newData.overview)
        assertEquals(mockMovieResponse.popularity, newData.popularity, 0.0)
        assertEquals(mockMovieResponse.originalLanguage, newData.originalLanguage)
        assertEquals(mockMovieResponse.hasVideo, newData.hasVideo)
        assertEquals(mockMovieResponse.genreIds, newData.genreIds)
        assertEquals(mockMovieResponse.posterPath, newData.posterPath)
        assertEquals(mockMovieResponse.backdropPath, newData.backdropPath)
        assertEquals(mockMovieResponse.releaseDate, newData.releaseDate)
        assertEquals(mockMovieResponse.isAdult, newData.isAdult)
        assertEquals(mockMovieResponse.voteAverage, newData.voteAverage, 0.0)
        assertEquals(mockMovieResponse.voteCount, newData.voteCount)
    }

    private fun testMovieResultMapper(newData: MoviesResultModel) {
        assertEquals(mockMoviesResultResponse.results.map { it.title }, newData.results.map { it.title })
    }

    private fun getCategory(): CATEGORY {
        val categories = CATEGORY.values()
        return categories[Random.nextInt(categories.size)]
    }
}