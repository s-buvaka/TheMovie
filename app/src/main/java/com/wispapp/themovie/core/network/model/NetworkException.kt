package com.wispapp.themovie.core.network.model

import com.google.gson.annotations.SerializedName

data class NetworkException(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("success") val isSuccessful: Boolean
) : Exception()
