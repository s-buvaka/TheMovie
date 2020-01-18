package com.wispapp.themovie.core.model.cache

import com.wispapp.themovie.core.model.database.ConfigDao
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.SourceType
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
        provider = DataBaseSourceCacheProvider(policy, type, dao)
    }

    @Test
    fun getAll() {
        `when`(type.name).thenReturn(SourceType.CONFIG.name)
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
}