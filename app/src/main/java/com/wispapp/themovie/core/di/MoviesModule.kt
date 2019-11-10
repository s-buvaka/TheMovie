@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.MoviesOverviewDao
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.DataSource
import com.wispapp.themovie.core.model.datasource.DataSourceImpl
import com.wispapp.themovie.core.model.network.ApiInterface
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.PopularMoviesProvider
import com.wispapp.themovie.core.model.network.mappers.MoviesOverviewMapper
import com.wispapp.themovie.core.model.network.models.movies.MovieOverviewResponse
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val moviesModule = module {

    factory<Mapper<MovieOverviewResponse, MovieOverviewModel>> { MoviesOverviewMapper() }

    factory<DataSource<MovieOverviewModel>> {
        DataSourceImpl<MovieOverviewModel>(
            get<NetworkProvider<MovieOverviewModel>>(),
            get<MoviesOverviewDao>()
        )
    }
    single {
        PopularMoviesProvider(
            get<Mapper<MovieOverviewResponse, MovieOverviewModel>>(),
            get<ApiInterface>()
        )
    }
    viewModel { MoviesViewModel(get<DataSource<MovieOverviewModel>>()) }
}