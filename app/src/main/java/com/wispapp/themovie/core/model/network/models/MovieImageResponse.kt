package com.wispapp.themovie.core.model.network.models

import com.google.gson.annotations.SerializedName

data class MovieImageResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("backdrops") val backdrops: List<ImageResponse>,
    @SerializedName("posters") val posters: List<ImageResponse>
)

data class ImageResponse(
    @SerializedName("aspect_ratio") val aspectRatio: Double,
    @SerializedName("file_path") val filePath: String,
    @SerializedName("iso_639_1") val iso6391: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)
