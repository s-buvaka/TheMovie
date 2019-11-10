@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.network.ApiInterface
import com.wispapp.themovie.core.network.PopularMoviesProvider
import com.wispapp.themovie.core.network.mappers.MoviesOverviewMapper
import com.wispapp.themovie.core.network.mappers.MoviesResultMapper
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {

    factory<MoviesOverviewMapper> { MoviesOverviewMapper() }
    factory<MoviesResultMapper> { MoviesResultMapper(get<MoviesOverviewMapper>()) }
    single {
        PopularMoviesProvider(
            get<MoviesResultMapper>(),
            get<ApiInterface>()
        )
    }
    viewModel { MoviesViewModel(get<PopularMoviesProvider>()) }
}