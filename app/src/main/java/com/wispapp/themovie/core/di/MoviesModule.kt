@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.network.PopularMoviesProvider
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {
    // single<MovieRepository> { MovieRepositoryImpl(get<ApiInterface>()) }
    single { PopularMoviesProvider(get()) }
    viewModel { MoviesViewModel(get<PopularMoviesProvider>()) }
    // viewModel { MoviesViewModel(get<MovieRepository>()) }
}