@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.RemoteDataSourceImpl
import com.wispapp.themovie.core.model.network.ApiInterface
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.PopularMoviesProvider
import com.wispapp.themovie.core.model.network.mappers.MoviesOverviewMapper
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATA_SOURCE_MOVIES_OVERVIEW = "data_source_movies_overview"
private const val MAPPER_MOVIES_OVERVIEW = "mapper_movies_overview"

val moviesModule = module {

    factory(named(MAPPER_MOVIES_OVERVIEW)) { MoviesOverviewMapper() }

    single<NetworkProvider<MovieOverviewModel>> {
        PopularMoviesProvider(
            get(named(MAPPER_MOVIES_OVERVIEW)),
            get<ApiInterface>()
        )
    }

    factory(named(DATA_SOURCE_MOVIES_OVERVIEW)) {
        RemoteDataSourceImpl<MovieOverviewModel>(
            get<NetworkProvider<MovieOverviewModel>>()
        )
    }

    viewModel { MoviesViewModel(get(named(DATA_SOURCE_MOVIES_OVERVIEW))) }
}