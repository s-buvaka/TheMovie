package com.wispapp.themovie.core.model.network

interface ArgumentsWrapper

data class MovieId(val movieId: Int) : ArgumentsWrapper