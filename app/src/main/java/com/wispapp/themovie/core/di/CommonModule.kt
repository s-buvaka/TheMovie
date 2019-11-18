@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import android.util.DisplayMetrics
import com.squareup.picasso.Picasso
import com.wispapp.themovie.core.application.App
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.common.ImageLoaderImpl
import org.koin.dsl.module

val commonModule = module {

    factory<Picasso> { Picasso.get() }
    factory<DisplayMetrics> { getDisplayDensity() }

    single<ImageLoader> { ImageLoaderImpl(get<Picasso>(), get<DisplayMetrics>()) }
}

private fun getDisplayDensity() = App.applicationContext().resources.displayMetrics