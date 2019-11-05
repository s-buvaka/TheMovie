package com.wispapp.themovie.core.network.model.configs

import com.google.gson.annotations.SerializedName

data class ConfigResponse(
    @SerializedName("images") val images: Images,
    @SerializedName("change_keys") val changeKeys: List<String>
)
