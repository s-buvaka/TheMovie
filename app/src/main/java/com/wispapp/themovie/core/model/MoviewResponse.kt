package com.wispapp.themovie.core.model

import com.google.gson.annotations.SerializedName

data class MoviewResponse(
    @SerializedName("page") val page: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0,
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("results") val results: List<MovieOverview> = emptyList()
)
