@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.network.ApiInterface
import com.wispapp.themovie.core.repository.MovieRepository
import com.wispapp.themovie.core.repository.impl.MovieRepositoryImpl
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get<ApiInterface>()) }
    viewModel { MoviesViewModel(get<MovieRepository>()) }
}