@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import androidx.room.Room
import com.wispapp.themovie.core.model.database.AppDataBase
import com.wispapp.themovie.core.model.database.ConfigDao
import com.wispapp.themovie.core.model.database.MovieDetailsDao
import com.wispapp.themovie.core.model.database.MoviesOverviewDao
import org.koin.android.ext.koin.androidContext
import org.koin.core.scope.Scope
import org.koin.dsl.module

val dataModule = module {

    single<AppDataBase> { createDBInstance() }
    single<ConfigDao> { get<AppDataBase>().getConfigDao() }
    single<MoviesOverviewDao> { get<AppDataBase>().getMoviesOverviewDao() }
    single<MovieDetailsDao> { get<AppDataBase>().getMovieDetailsDao() }
}

private fun Scope.createDBInstance() =
    Room.databaseBuilder(androidContext(), AppDataBase::class.java, "the_movie_db").build()