package com.wispapp.themovie.core.model.cache

import org.junit.Assert.assertEquals
import org.junit.Test

class TimeoutCachePolicyTest {

    private val cachePolicy = TimeoutCachePolicyImpl(10_000)

    @Test
    fun isValid() {
        isValidSuccess()
        isValidFail()
    }

    private fun isValidSuccess() {
        cachePolicy.setTimeStamp(System.currentTimeMillis() - 1000)
        val isValid = cachePolicy.isValid()
        assert(isValid)
    }

    private fun isValidFail() {
        cachePolicy.setTimeStamp(System.currentTimeMillis() - 100_000)
        val isValid = cachePolicy.isValid()
        assert(!isValid)
    }

    @Test
    fun getTimeStamp() {
        val timeStamp = System.currentTimeMillis()
        cachePolicy.setTimeStamp(timeStamp)
        val timeStampFromPolicy = cachePolicy.getTimeStamp()
        assertEquals(timeStamp, timeStampFromPolicy)
    }
}