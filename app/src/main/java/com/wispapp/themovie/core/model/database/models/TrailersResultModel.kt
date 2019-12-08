package com.wispapp.themovie.core.model.database.models

data class TrailersResultModel(
    val id: Int,
    val results: List<TrailerModel>
)

data class TrailerModel(
    val id: String,
    val iso6391: String,
    val iso31661: String,
    val key: String,
    val name: String,
    val site: String,
    val size: Int,
    val type: String
)