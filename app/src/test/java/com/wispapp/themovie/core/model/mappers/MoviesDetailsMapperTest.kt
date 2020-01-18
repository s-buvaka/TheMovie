package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.model.database.models.*
import com.wispapp.themovie.core.model.network.models.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class MoviesDetailsMapperTest {

    private val genresMapper = GenresMapper()
    private val companiesMapper = ProductionCompaniesMapper()
    private val countriesMapper = ProductionCountriesMapper()
    private val languagesMapper = SpokenLanguagesMapper()
    private val detailsMapper = MoviesDetailsMapper(genresMapper, companiesMapper, countriesMapper, languagesMapper)

    private lateinit var mockGenresResponse: GenresItemResponse
    private lateinit var mockCompaniesResponse: ProductionCompaniesItemResponse
    private lateinit var mockCountriesResponse: ProductionCountriesItemResponse
    private lateinit var mockLanguageResponse: SpokenLanguagesItemResponse
    private lateinit var mockMovieDetailsResponse: MovieDetailsResponse

    @Before
    fun setUp() {
        mockGenresResponse = GenresItemResponse(Random.nextInt(0, Int.MAX_VALUE), "comedy")
        mockCompaniesResponse = ProductionCompaniesItemResponse(
            id = Random.nextInt(0, Int.MAX_VALUE),
            logoPath = "Warner Brazers",
            name = "Joker",
            originCountry = "USA"
        )
        mockCountriesResponse = ProductionCountriesItemResponse(isoCode = "iso565464", name = "Batman")
        mockLanguageResponse = SpokenLanguagesItemResponse(isoCode = "iso658974", name = "English")
        mockMovieDetailsResponse = MovieDetailsResponse(
            id = Random.nextInt(0, Int.MAX_VALUE),
            adult = false,
            backdropPath = "/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
            budget = Random.nextInt(0, Int.MAX_VALUE),
            genres = listOf(mockGenresResponse, mockGenresResponse),
            homepage = "http://www.jokermovie.net/",
            imdbId = "tt7286456",
            originalLanguage = "en",
            originalTitle = "Fast and Furious",
            overview = "During the 1980s, a failed stand-up comedian",
            popularity = 8.8,
            posterPath = "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            companies = listOf(mockCompaniesResponse, mockCompaniesResponse),
            countries = listOf(mockCountriesResponse, mockCountriesResponse),
            releaseDate = "2019-10-02",
            revenue = 1060753468,
            runtime = 122,
            spokenLanguages = listOf(mockLanguageResponse, mockLanguageResponse),
            status = "Released",
            tagLine = "Put on a happy face.",
            title = "Joker",
            hasVideo = false,
            voteAverage = 8.3,
            voteCount = Random.nextInt(0, Int.MAX_VALUE)
        )
    }

    @Test
    fun movies_details_map_from_test() {
        val newGenresData = genresMapper.mapFrom(mockGenresResponse)
        val newCompaniesData = companiesMapper.mapFrom(mockCompaniesResponse)
        val newCountriesData = countriesMapper.mapFrom(mockCountriesResponse)
        val newLanguageData = languagesMapper.mapFrom(mockLanguageResponse)
        val newMovieDetailsData = detailsMapper.mapFrom(mockMovieDetailsResponse)

        testGenresMapper(newGenresData)
        testCompaniesMapper(newCompaniesData)
        testCountriesMapper(newCountriesData)
        testLanguageMapper(newLanguageData)
        testDetailsMapper(newMovieDetailsData)
    }

    private fun testGenresMapper(newData: GenresItemModel) {
        assertEquals(mockGenresResponse.id, newData.id)
        assertEquals(mockGenresResponse.name, newData.name)
    }

    private fun testCompaniesMapper(newData: ProductionCompaniesItemModel) {
        assertEquals(mockCompaniesResponse.id, newData.id)
        assertEquals(mockCompaniesResponse.logoPath, newData.logoPath)
        assertEquals(mockCompaniesResponse.name, newData.name)
        assertEquals(mockCompaniesResponse.originCountry, newData.originCountry)
    }

    private fun testCountriesMapper(newData: ProductionCountriesItemModel) {
        assertEquals(mockCountriesResponse.isoCode, newData.isoCode)
        assertEquals(mockCountriesResponse.name, newData.name)
    }

    private fun testLanguageMapper(newData: SpokenLanguagesItemModel) {
        assertEquals(mockLanguageResponse.isoCode, newData.isoCode)
        assertEquals(mockLanguageResponse.name, newData.name)
    }

    private fun testDetailsMapper(newData: MovieDetailsModel) {
        assertEquals(mockMovieDetailsResponse.id, newData.id)
        assertEquals(mockMovieDetailsResponse.adult, newData.adult)
        assertEquals(mockMovieDetailsResponse.backdropPath, newData.backdropPath)
        assertEquals(mockMovieDetailsResponse.budget, newData.budget)
        assertEquals(mockMovieDetailsResponse.genres.map { it.name }, newData.genres.map { it.name })
        assertEquals(mockMovieDetailsResponse.homepage, newData.homepage)
        assertEquals(mockMovieDetailsResponse.imdbId, newData.imdbId)
        assertEquals(mockMovieDetailsResponse.originalLanguage, newData.originalLanguage)
        assertEquals(mockMovieDetailsResponse.originalTitle, newData.originalTitle)
        assertEquals(mockMovieDetailsResponse.overview, newData.overview)
        assertEquals(mockMovieDetailsResponse.popularity, newData.popularity, 0.0)
        assertEquals(mockMovieDetailsResponse.posterPath, newData.posterPath)
        assertEquals(mockMovieDetailsResponse.companies.map { it.name }, newData.companies.map { it.name })
        assertEquals(mockMovieDetailsResponse.countries.map { it.name }, newData.countries.map { it.name })
        assertEquals(mockMovieDetailsResponse.releaseDate, newData.releaseDate)
        assertEquals(mockMovieDetailsResponse.revenue, newData.revenue)
        assertEquals(mockMovieDetailsResponse.runtime, newData.runtime)
        assertEquals(mockMovieDetailsResponse.spokenLanguages.map { it.name }, newData.spokenLanguages.map { it.name })
        assertEquals(mockMovieDetailsResponse.status, newData.status)
        assertEquals(mockMovieDetailsResponse.tagLine, newData.tagLine)
        assertEquals(mockMovieDetailsResponse.title, newData.title)
        assertEquals(mockMovieDetailsResponse.hasVideo, newData.hasVideo)
        assertEquals(mockMovieDetailsResponse.voteAverage, newData.voteAverage, 0.0)
        assertEquals(mockMovieDetailsResponse.voteCount, newData.voteCount)
    }
}