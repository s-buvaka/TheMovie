package com.wispapp.themovie.core.application

import android.app.Application
import com.wispapp.themovie.core.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext
import org.koin.core.context.loadKoinModules
import org.koin.core.logger.Level

class KoinStarter {

    private var koinApp: KoinApplication? = null

    fun start(application: Application) {
        koinApp = getOrCreateKoinApplication(application)
    }

    private fun getOrCreateKoinApplication(application: Application): KoinApplication {
        return GlobalContext.getOrNull()?.apply {
            loadKoinModules(getModules())
        } ?: startKoin(application)
    }

    private fun startKoin(application: Application): KoinApplication {
        return org.koin.core.context.startKoin {
            androidContext(application)
            androidLogger(level = Level.DEBUG)
            modules(getModules())
        }
    }

    private fun getModules() =
        listOf(
            commonModule,
            configsModule,
            dataModule,
            moviesModule,
            networkModule,
            searchModule
        )
}