package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.model.database.models.ReviewModel
import com.wispapp.themovie.core.model.database.models.ReviewResultModel
import com.wispapp.themovie.core.model.network.models.ReviewResponse
import com.wispapp.themovie.core.model.network.models.ReviewResultResponse
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.random.Random

class ReviewResultMapperTest {

    private val reviewMapper = ReviewMapper()
    private val reviewResultMapper = ReviewResultMapper(reviewMapper)

    private lateinit var mockReviewResponse: ReviewResponse
    private lateinit var mockReviewResultResponse: ReviewResultResponse

    @Before
    fun setUp() {
        mockReviewResponse = generateReview()

        mockReviewResultResponse =
            ReviewResultResponse(
                id = Random.nextInt(0, Int.MAX_VALUE),
                page = Random.nextInt(0, Int.MAX_VALUE),
                totalPages = Random.nextInt(0, Int.MAX_VALUE),
                totalResults = Random.nextInt(0, Int.MAX_VALUE),
                results = listOf(
                    generateReview(),
                    generateReview(),
                    generateReview(),
                    generateReview(),
                    generateReview()
                )
            )
    }

    @Test
    fun review_map_from_test() {
        val newReviewData = reviewMapper.mapFrom(mockReviewResponse)
        val newReviewResultData = reviewResultMapper.mapFrom(mockReviewResultResponse)

        testReviewMapper(newReviewData)
        testReviewResultMapper(newReviewResultData)
    }

    private fun generateReview(): ReviewResponse {
        val ids = listOf(
            "5d975abdae26be001abe129b",
            "57a814dc9251415cfb00309a",
            "5dac3463174a870019a7c547",
            "5db52049d40d4c0014fd08bc",
            "5dd9cdd528723c0014588c1f"
        )
        val authors = listOf("Frank Ochieng", "Leno", "Wuchak", "ikomrad", "Sheldon Nylander")
        val contents = listOf(
            "Summertime 2016",
            "Okay, this film has already been",
            "Joker. The character that has existed since 1940",
            "If you enjoy reading my Spoiler-Free reviews",
            "***Not fun, but absorbing, artistic and tragic***"
        )
        val urls = listOf(
            "https://www.themoviedb.org/review/5de278e63faba000150b67f4",
            "https://www.themoviedb.org/review/57a814dc9251415cfb00309",
            "https://www.themoviedb.org/review/5dd9cdd528723c0014588c1f",
            "https://www.themoviedb.org/review/5df59eaad1a89300197854b5",
            "https://www.themoviedb.org/review/5e0e888768188800145829f8"
        )

        val id = ids[Random.nextInt(ids.size)]
        val author = authors[Random.nextInt(authors.size)]
        val content = contents[Random.nextInt(contents.size)]
        val url = urls[Random.nextInt(urls.size)]

        return ReviewResponse(id, author, content, url)
    }

    private fun testReviewMapper(newData: ReviewModel) {
        assertEquals(mockReviewResponse.id, newData.id)
        assertEquals(mockReviewResponse.author, newData.author)
        assertEquals(mockReviewResponse.content, newData.content)
        assertEquals(mockReviewResponse.url, newData.url)
    }

    private fun testReviewResultMapper(newData: ReviewResultModel) {
        assertEquals(mockReviewResultResponse.id, newData.id)
        assertEquals(mockReviewResultResponse.page, newData.page)
        assertEquals(mockReviewResultResponse.totalPages, newData.totalPages)
        assertEquals(mockReviewResultResponse.totalResults, newData.totalResults)
        assertEquals(mockReviewResultResponse.results.size, newData.results.size)
        assertEquals(mockReviewResultResponse.results.map { it.id }, newData.results.map { it.id })
        assertEquals(mockReviewResultResponse.results.map { it.author }, newData.results.map { it.author })
        assertEquals(mockReviewResultResponse.results.map { it.content }, newData.results.map { it.content })
        assertEquals(mockReviewResultResponse.results.map { it.url }, newData.results.map { it.url })
    }
}