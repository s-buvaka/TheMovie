package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.model.database.models.TrailerModel
import com.wispapp.themovie.core.model.database.models.TrailersResultModel
import com.wispapp.themovie.core.model.network.models.TrailerResponse
import com.wispapp.themovie.core.model.network.models.TrailersResultResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class TrailerMapperTest {

    private val trailerMapper = TrailerMapper()
    private val trailerResultMapper = TrailerResultMapper(trailerMapper)

    private lateinit var mockTrailerResponse: TrailerResponse
    private lateinit var mockTrailerResultResponse: TrailersResultResponse

    @Before
    fun setUp() {
        mockTrailerResponse = TrailerResponse(
            id = "5ccf56c8925141044e2c88d6",
            iso6391 = "en",
            iso31661 = "US",
            key = "t433PEQGErc",
            name = "JOKER - Teaser Trailer",
            site = "YouTube",
            size = 1080,
            type = "Trailer"
        )

        mockTrailerResultResponse = TrailersResultResponse(
            id = Random.nextInt(0, Int.MAX_VALUE),
            results = listOf(mockTrailerResponse, mockTrailerResponse, mockTrailerResponse)
        )
    }

    @Test
    fun trailer_map_from_test() {
        val newTrailerData = trailerMapper.mapFrom(mockTrailerResponse)
        val newTrailerResultData = trailerResultMapper.mapFrom(mockTrailerResultResponse)

        testTrailerMapper(newTrailerData)
        testTrailerResultMapper(newTrailerResultData)
    }

    private fun testTrailerMapper(newData: TrailerModel) {
        assertEquals(mockTrailerResponse.id, newData.id)
        assertEquals(mockTrailerResponse.iso6391, newData.iso6391)
        assertEquals(mockTrailerResponse.iso31661, newData.iso31661)
        assertEquals(mockTrailerResponse.key, newData.key)
        assertEquals(mockTrailerResponse.name, newData.name)
        assertEquals(mockTrailerResponse.site, newData.site)
        assertEquals(mockTrailerResponse.size, newData.size)
        assertEquals(mockTrailerResponse.type, newData.type)
    }

    private fun testTrailerResultMapper(newData: TrailersResultModel) {
        assertEquals(mockTrailerResultResponse.id, newData.id)
        assertEquals(mockTrailerResultResponse.results.map { it.id }, newData.results.map { it.id })
    }
}