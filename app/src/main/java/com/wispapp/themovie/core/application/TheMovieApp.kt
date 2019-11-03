package com.wispapp.themovie.core.application

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class TheMovieApp : Application() {

    private val koinStarter = KoinStarter()

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initStetho()
        initKoin()
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initKoin() {
        koinStarter.start(this)
    }

    companion object {

        lateinit var instance: TheMovieApp

        fun applicationContext(): Context = instance.applicationContext
    }
}