package com.wispapp.themovie.core.model.cache

interface TimeoutCachePolicy {
    fun isValid(): Boolean
    fun setTimeStamp(timeStamp: Long)
    fun getTimeStamp(): Long
}

class TimeoutCachePolicyImpl(private val cacheTimeOut: Long) : TimeoutCachePolicy {

    private var lastKnownTimeStamp = 0L

    override fun isValid() = (System.currentTimeMillis() - lastKnownTimeStamp) < cacheTimeOut

    override fun setTimeStamp(timeStamp: Long) {
        lastKnownTimeStamp = timeStamp
    }

    override fun getTimeStamp() = lastKnownTimeStamp
}