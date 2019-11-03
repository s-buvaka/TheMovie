@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.wispapp.themovie.core.network.ApiInterface
import com.wispapp.themovie.core.network.ApiProvider
import com.wispapp.themovie.core.network.BaseUrlProvider
import com.wispapp.themovie.core.network.BaseUrlProviderImpl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val TIMEOUT_IN_SECOND = 10

val networkModule = module {
    factory<OkHttpClient> { buildOkHttpClient() }
    single<BaseUrlProvider> { BaseUrlProviderImpl() }
    factory<Retrofit> { buildRetrofit(get<BaseUrlProvider>(), get<OkHttpClient>()) }
    factory<ApiInterface> { ApiProvider(get<Retrofit>()).provide() }
}

private val loggingInterceptor = HttpLoggingInterceptor()

private fun buildOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(TIMEOUT_IN_SECOND.toLong(), TimeUnit.SECONDS)
        .connectTimeout(TIMEOUT_IN_SECOND.toLong(), TimeUnit.SECONDS)
        .build()
}

private fun buildRetrofit(urlProvider: BaseUrlProvider, client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(urlProvider.provide())
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
}