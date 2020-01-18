package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.model.database.models.ImageModel
import com.wispapp.themovie.core.model.database.models.ImagesResultModel
import com.wispapp.themovie.core.model.network.models.ImageResponse
import com.wispapp.themovie.core.model.network.models.ImagesResultResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class ImagesResultMapperTest {

    private val imagesMapper = ImagesMapper()
    private val imageResultMapper = ImagesResultMapper(imagesMapper)

    private lateinit var mockImageResponse: ImageResponse
    private lateinit var mockImageResultResponse: ImagesResultResponse

    @Before
    fun setUp() {
        mockImageResponse = generateImage()

        mockImageResultResponse = ImagesResultResponse(
            id = Random.nextInt(0, Int.MAX_VALUE),
            backdrops = listOf(generateImage(), generateImage(), generateImage(), generateImage(), generateImage()),
            posters = listOf(generateImage(), generateImage(), generateImage(), generateImage(), generateImage())
        )
    }

    @Test
    fun mapFrom() {
        val newImageData = imagesMapper.mapFrom(mockImageResponse)
        val newImageResultData = imageResultMapper.mapFrom(mockImageResultResponse)

        testImagesMapper(newImageData)
        testImageResultMapper(newImageResultData)
    }

    private fun generateImage(): ImageResponse {
        val sizes = listOf(180, 300, 400, 720, 1280)
        val filePaths = listOf(
            "/fCayJrkfRaCRCTh8GqN30f8oyQF.jpg",
            "/fpemzjF623QVTe98pCVlwwtFC5N.jpg",
            "/fpemzjF623QVT22228pCVlwwtFC5N.jpg",
            "/fpemzjF623QfasdVTe98pCVlwwtFC5N.jpg",
            "/fpsaf24343623QfasdVTe98pCVlwwtFC5N.jpg"
        )

        val aspectRatio = Random.nextDouble(1.00000000, 2.00000000)
        val filePath = filePaths[Random.nextInt(sizes.size)]
        val height = sizes[Random.nextInt(sizes.size)]
        val iso6391 = null
        val voteAverage = Random.nextDouble(10.0)
        val voteCount = Random.nextInt(0, Int.MAX_VALUE)
        val width = sizes[Random.nextInt(sizes.size)]

        return ImageResponse(aspectRatio, filePath, iso6391, voteAverage, voteCount, width, height)
    }

    private fun testImagesMapper(newData: ImageModel) {
        assertEquals(mockImageResponse.aspectRatio, newData.aspectRatio, 0.0)
        assertEquals(mockImageResponse.filePath, newData.filePath)
        assertEquals(mockImageResponse.height, newData.height)
        assertEquals(mockImageResponse.iso6391, newData.iso6391)
        assertEquals(mockImageResponse.voteAverage, newData.voteAverage, 0.0)
        assertEquals(mockImageResponse.voteCount, newData.voteCount)
        assertEquals(mockImageResponse.width, newData.width)
    }

    private fun testImageResultMapper(newData: ImagesResultModel) {
        assertEquals(mockImageResultResponse.id, newData.id)
        assertEquals(mockImageResultResponse.backdrops.size, newData.backdrops.size)
        assertEquals(mockImageResultResponse.backdrops.map { it.filePath }, newData.backdrops.map { it.filePath })
        assertEquals(mockImageResultResponse.posters.size, newData.posters.size)
        assertEquals(mockImageResultResponse.posters.map { it.filePath }, newData.posters.map { it.filePath })
    }
}