package com.wispapp.themovie.core.model.database.models

data class ReviewResultModel(
    val id: Int,
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val results: List<ReviewModel>
)

data class ReviewModel(
    val id: String,
    val author: String,
    val content: String,
    val url: String
)