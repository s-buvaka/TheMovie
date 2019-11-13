package com.wispapp.themovie.core.model.network

interface RequestWrapper

data class MovieId(val movieId: Int) : RequestWrapper