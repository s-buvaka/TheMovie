package com.wispapp.themovie.core.model.cache

import com.wispapp.themovie.core.model.database.ConfigDao
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.ImagesConfigModel
import com.wispapp.themovie.core.model.database.models.SourceType
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

class DataBaseSourceCacheProviderTest {

    private val dao = mock(ConfigDao::class.java)
    private val type = mock(SourceType::class.java)
    private val policy = mock(TimeoutCachePolicy::class.java)

    private lateinit var provider: DataBaseSourceCacheProvider<ConfigModel>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getAllCachedData() {
        val data = generateData()

        `when`(policy.isValid()).thenReturn(true)
        `when`(dao.getAll()).thenReturn(data)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val result = runBlocking { provider.getAll() }

        assertEquals(result is CacheState.Actual, true)

        val dataFromCache = (result as CacheState.Actual).data

        assertThat(dataFromCache.size, `is`(data.size))
        dataFromCache.forEachIndexed { index, model ->
            assertEquals(data[index], model)
        }
    }

    @Test
    fun getById() {
    }

    @Test
    fun putAll() {
    }

    @Test
    fun put() {
    }

    private fun generateData(): List<ConfigModel> {
        val configModel = ImagesConfigModel(
            baseUrl = "http://image.tmdb.org/t/p/",
            secureBaseUrl = "https://image.tmdb.org/t/p/",
            posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
            backdropSizes = listOf("w300", "w780", "w1280", "original"),
            logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
            stillSizes = listOf("w92", "w185", "w300", "original"),
            profileSizes = listOf("w45", "w185", "h632", "original")
        )

        val changeKeys = listOf(
            "adult", "air_date", "also_known_as", "alternative_titles", "biography",
            "birthday", "budget", "cast", "certifications", "character_names", "created_by",
            "crew", "deathday", "episode", "episode_number", "episode_run_time", "freebase_id",
            "freebase_mid", "general", "genres", "guest_stars", "homepage", "images", "imdb_id",
            "languages", "name", "network", "origin_country", "original_name", "original_title",
            "overview", "parts", "place_of_birth", "plot_keywords", "production_code",
            "production_companies", "production_countries", "releases", "revenue", "runtime",
            "season", "season_number", "season_regular", "spoken_languages", "status",
            "tagline", "title", "translations", "tvdb_id", "tvrage_id", "type", "video", "videos"
        )
        return listOf(
            ConfigModel(1, configModel, changeKeys),
            ConfigModel(2, configModel, changeKeys),
            ConfigModel(3, configModel, changeKeys)
        )
    }
}