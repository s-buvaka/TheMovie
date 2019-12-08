package com.wispapp.themovie.core.model.network.models

import com.google.gson.annotations.SerializedName

data class ReviewResultResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("results") val results: List<ReviewResponse>
)

data class ReviewResponse(
    @SerializedName("id") val id: String,
    @SerializedName("author") val author: String,
    @SerializedName("content") val content: String,
    @SerializedName("url") val url: String
)
