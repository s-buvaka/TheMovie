@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.squareup.picasso.Picasso
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.common.ImageLoaderImpl
import org.koin.dsl.module

val commonModule = module {

    factory<Picasso> { Picasso.get() }
    factory<ImageLoader> { ImageLoaderImpl(get<Picasso>()) }
}