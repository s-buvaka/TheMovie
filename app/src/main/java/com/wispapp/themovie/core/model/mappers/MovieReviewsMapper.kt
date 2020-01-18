package com.wispapp.themovie.core.model.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.ReviewModel
import com.wispapp.themovie.core.model.database.models.ReviewResultModel
import com.wispapp.themovie.core.model.network.models.ReviewResponse
import com.wispapp.themovie.core.model.network.models.ReviewResultResponse

class ReviewResultMapper(private val mapper: Mapper<ReviewResponse, ReviewModel>) :
    Mapper<ReviewResultResponse, ReviewResultModel> {

    override fun mapFrom(source: ReviewResultResponse): ReviewResultModel =
        ReviewResultModel(
            id = source.id,
            page = source.page,
            totalPages = source.totalPages,
            totalResults = source.totalResults,
            results = source.results.map { mapper.mapFrom(it) }
        )
}

class ReviewMapper : Mapper<ReviewResponse, ReviewModel> {

    override fun mapFrom(source: ReviewResponse): ReviewModel =
        ReviewModel(
            id = source.id,
            author = source.author,
            content = source.content,
            url = source.url
        )
}