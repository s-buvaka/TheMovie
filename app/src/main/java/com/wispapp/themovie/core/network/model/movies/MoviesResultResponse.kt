package com.wispapp.themovie.core.network.model.movies

import com.google.gson.annotations.SerializedName

data class MoviesResultResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("results") val results: List<MovieOverviewResponse>
)
