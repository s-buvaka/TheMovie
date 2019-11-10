package com.wispapp.themovie.core.model.network.models.movies

import com.google.gson.annotations.SerializedName

data class MovieOverviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("video") val hasVideo: Boolean = false,
    @SerializedName("genre_ids") val genreIds: List<Int> = emptyList(),
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("adult") val isAdult: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
