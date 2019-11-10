@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.repository.AppDataRepository
import com.wispapp.themovie.core.viewmodel.AppDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appDataModule = module {
    // single<AppDataRepository> { AppDataRepositoryImpl(get<ApiInterface>()) }
    viewModel { AppDataViewModel(get<AppDataRepository>()) }
}