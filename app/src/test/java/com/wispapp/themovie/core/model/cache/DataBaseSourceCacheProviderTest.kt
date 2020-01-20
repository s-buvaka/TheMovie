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
import org.mockito.Mockito.*
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

        val data = listOf(
            ConfigModel(1, generateImageConfigModel(), generateChangeKeys()),
            ConfigModel(2, generateImageConfigModel(), generateChangeKeys()),
            ConfigModel(3, generateImageConfigModel(), generateChangeKeys())
        )

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
    fun getCachedDataById() {

        val data = ConfigModel(1, generateImageConfigModel(), generateChangeKeys())

        `when`(policy.isValid()).thenReturn(true)
        `when`(dao.getById(1)).thenReturn(data)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val resultSuccess = runBlocking { provider.getById(1) }
        val resultNull = runBlocking { provider.getById(0) }

        assertEquals(resultSuccess is CacheState.Actual, true)
        assertEquals(resultNull is CacheState.Empty, true)

        val dataFromCache = (resultSuccess as CacheState.Actual).data
        assertEquals(dataFromCache, data)
    }

    @Test
    fun getNoCachedAllData() {

        `when`(policy.isValid()).thenReturn(false)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val result = runBlocking { provider.getAll() }
        assertEquals(result is CacheState.Empty, true)
    }

    @Test
    fun getByIdNoCached() {

        `when`(policy.isValid()).thenReturn(false)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val result = runBlocking { provider.getById(1) }
        assertEquals(result is CacheState.Empty, true)
    }

    @Test
    fun putAll() {

        `when`(policy.getTimeStamp()).thenReturn(0L)
        `when`(type.name).thenReturn(SourceType.CONFIG.name)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val data = listOf(
            ConfigModel(1, generateImageConfigModel(), generateChangeKeys()),
            ConfigModel(2, generateImageConfigModel(), generateChangeKeys()),
            ConfigModel(3, generateImageConfigModel(), generateChangeKeys())
        )

        runBlocking { provider.putAll(data) }
        verify(dao).insertAll(data)
    }

    @Test
    fun put() {

        `when`(policy.getTimeStamp()).thenReturn(0L)
        `when`(type.name).thenReturn(SourceType.CONFIG.name)

        provider = DataBaseSourceCacheProvider(policy, type, dao)

        val data = ConfigModel(1, generateImageConfigModel(), generateChangeKeys())

        runBlocking { provider.put(data) }
        verify(dao).insert(data)
    }

    private fun generateImageConfigModel() =
        ImagesConfigModel(
            baseUrl = "http://image.tmdb.org/t/p/",
            secureBaseUrl = "https://image.tmdb.org/t/p/",
            posterSizes = listOf("w92", "w154", "w185", "w342", "w500", "w780", "original"),
            backdropSizes = listOf("w300", "w780", "w1280", "original"),
            logoSizes = listOf("w45", "w92", "w154", "w185", "w300", "w500", "original"),
            stillSizes = listOf("w92", "w185", "w300", "original"),
            profileSizes = listOf("w45", "w185", "h632", "original")
        )

    private fun generateChangeKeys() =
        listOf(
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

}