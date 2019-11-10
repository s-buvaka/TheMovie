package com.wispapp.themovie.core.model.network.models

import com.google.gson.annotations.SerializedName

data class NetworkException(
    @SerializedName("status_code") val statusCode: Int = 0,
    @SerializedName("status_message") val statusMessage: String = "",
    @SerializedName("success") val isSuccessful: Boolean = false
) : Exception()
