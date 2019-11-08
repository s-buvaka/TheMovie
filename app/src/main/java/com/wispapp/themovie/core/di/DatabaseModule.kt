//@file:Suppress("RemoveExplicitTypeArguments")
//
//package com.wispapp.themovie.core.di
//
//import androidx.room.Room
//import com.wispapp.themovie.core.database.AppDataBase
//import com.wispapp.themovie.core.database.ConfigDao
//import org.koin.android.ext.koin.androidContext
//import org.koin.core.scope.Scope
//import org.koin.dsl.module
//
//val databaseModule = module {
//    single<AppDataBase> { createDBInstance() }
//    single<ConfigDao> { get<AppDataBase>().configDao() }
//}
//
//private fun Scope.createDBInstance() =
//    Room.databaseBuilder(androidContext(), AppDataBase::class.java, "the_movie_db").build()