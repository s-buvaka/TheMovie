package com.wispapp.themovie.core.mappers

interface Mapper<DAO, RESPONSE> {

    fun mapFromDao(dao: DAO): RESPONSE

    fun mapToDao(response: RESPONSE): DAO
}