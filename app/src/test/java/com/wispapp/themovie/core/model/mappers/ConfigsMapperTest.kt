package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.ImagesConfigModel
import com.wispapp.themovie.core.model.network.models.ConfigResponse
import com.wispapp.themovie.core.model.network.models.ImagesConfigResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ConfigsMapperTest {

    private val imageConfigMapper = ImageConfigMapper()
    private val configMapper = ConfigsMapper(imageConfigMapper)

    private lateinit var mockImagesConfigResponse: ImagesConfigResponse
    private lateinit var mockConfigResponse: ConfigResponse

    @Before
    fun setUp() {
        mockImagesConfigResponse = ImagesConfigResponse(
            baseUrl = "http://image.tmdb.org/t/p/",
            secureBaseUrl = "https://image.tmdb.org/t/p/",
            posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
            backdropSizes = listOf("w300", "w780", "w1280", "original"),
            logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
            stillSizes = listOf("w92", "w185", "w300", "original"),
            profileSizes = listOf("w45", "w185", "h632", "original")
        )

        val mockChangeKeys = listOf(
            "adult", "air_date", "also_known_as", "alternative_titles", "biography",
            "birthday", "budget", "cast", "certifications", "character_names", "created_by",
            "crew", "deathday", "episode", "episode_number", "episode_run_time", "freebase_id",
            "freebase_mid", "general", "genres", "guest_stars", "homepage", "images", "imdb_id",
            "languages", "name", "network", "origin_country", "original_name", "original_title",
            "overview", "parts", "place_of_birth", "plot_keywords", "production_code", "production_companies",
            "production_countries", "releases", "revenue", "runtime", "season", "season_number",
            "season_regular", "spoken_languages", "status", "tagline", "title", "translations",
            "tvdb_id", "tvrage_id", "type", "video", "videos"
        )

        mockConfigResponse = ConfigResponse(images = mockImagesConfigResponse, changeKeys = mockChangeKeys)
    }

    @Test
    fun configs_map_from_test() {
        val newImagesConfigData = imageConfigMapper.mapFrom(mockImagesConfigResponse)
        val newConfigData = configMapper.mapFrom(mockConfigResponse)

        testImageConfigsMapper(newImagesConfigData)
        testConfigsMapper(newConfigData)
    }

    private fun testConfigsMapper(newData: ConfigModel) {
        assertEquals(mockConfigResponse.changeKeys, newData.changeKeys)
    }

    private fun testImageConfigsMapper(newData: ImagesConfigModel) {
        assertEquals(mockImagesConfigResponse.baseUrl, newData.baseUrl)
        assertEquals(mockImagesConfigResponse.secureBaseUrl, newData.secureBaseUrl)
        assertEquals(mockImagesConfigResponse.posterSizes, newData.posterSizes)
        assertEquals(mockImagesConfigResponse.backdropSizes, newData.backdropSizes)
        assertEquals(mockImagesConfigResponse.logoSizes, newData.logoSizes)
        assertEquals(mockImagesConfigResponse.stillSizes, newData.stillSizes)
        assertEquals(mockImagesConfigResponse.profileSizes, newData.profileSizes)
    }
}