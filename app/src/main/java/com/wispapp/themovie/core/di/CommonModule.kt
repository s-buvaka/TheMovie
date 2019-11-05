package com.wispapp.themovie.core.di

import com.squareup.picasso.Picasso
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.common.impl.ImageLoaderImpl
import org.koin.dsl.module

val commonModule = module {
    single<Picasso> { Picasso.get() }
    single<ImageLoader> { ImageLoaderImpl }
}