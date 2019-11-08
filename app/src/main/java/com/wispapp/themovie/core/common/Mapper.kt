package com.wispapp.themovie.core.common

interface Mapper<E, T> {

    fun mapFrom(source: E): T
}