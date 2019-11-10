@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import androidx.room.Room
import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.AppDataBase
import com.wispapp.themovie.core.model.database.ConfigDao
import com.wispapp.themovie.core.model.database.MoviesOverviewDao
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.datasource.DataSourceImpl
import com.wispapp.themovie.core.model.network.ApiInterface
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.PopularMoviesProvider
import com.wispapp.themovie.core.model.network.models.movies.MovieOverviewResponse
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

private const val MOVIES_OVERVIEW_DATA_SOURCE = "movies_overview_data_source"

val dataModule = module {

    single<AppDataBase> { createDBInstance() }
    single<ConfigDao> { get<AppDataBase>().getConfigDao() }
    single<MoviesOverviewDao> { get<AppDataBase>().getMoviesOverviewDao() }

    single<NetworkProvider<MovieOverviewModel>> {
        PopularMoviesProvider(
            get<Mapper<MovieOverviewResponse, MovieOverviewModel>>(),
            get<ApiInterface>()
        )
    }

    factory(named(MOVIES_OVERVIEW_DATA_SOURCE)) {
        DataSourceImpl(
            get<NetworkProvider<MovieOverviewModel>>(),
            get<MoviesOverviewDao>()
        )
    }
}

private fun Scope.createDBInstance() =
    Room.databaseBuilder(androidContext(), AppDataBase::class.java, "the_movie_db").build()