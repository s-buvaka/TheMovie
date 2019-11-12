@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.cache.TimeoutCachePolicyImpl
import com.wispapp.themovie.core.model.database.ConfigDao
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.SourceType
import com.wispapp.themovie.core.model.datasource.CachedDataSourceImpl
import com.wispapp.themovie.core.model.network.ApiInterface
import com.wispapp.themovie.core.model.network.ConfigsRemoteProvider
import com.wispapp.themovie.core.model.network.NetworkProvider
import com.wispapp.themovie.core.model.network.mappers.ConfigsMapper
import com.wispapp.themovie.core.model.network.mappers.ImageConfigMapper
import com.wispapp.themovie.core.viewmodel.AppDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATA_SOURCE_CONFIGS = "data_source_configs"
private const val MAPPER_IMAGE_CONFIGS = "mapper_image_configs"
private const val MAPPER_CONFIGS = "mapper_configs"

private const val DEFAULT_CACHE_POLICY = "default_cash_policy"
private const val DEFAULT_CACHE_TIMEOUT = 24 * 60 * 1000 * 60L

val appDataModule = module {

    factory(named(MAPPER_IMAGE_CONFIGS)) { ImageConfigMapper() }
    factory(named(MAPPER_CONFIGS)) { ConfigsMapper(get(named(MAPPER_IMAGE_CONFIGS))) }

    factory(named(DEFAULT_CACHE_POLICY)) { TimeoutCachePolicyImpl(DEFAULT_CACHE_TIMEOUT) }

    factory {
        DataBaseSourceCacheProvider<ConfigModel>(
            get(named(DEFAULT_CACHE_POLICY)),
            SourceType.CONFIG,
            get<ConfigDao>()
        )
    }

    factory(named(DATA_SOURCE_CONFIGS)) {
        CachedDataSourceImpl<ConfigModel>(
            get<NetworkProvider<ConfigModel>>(),
            get<DataBaseSourceCacheProvider<ConfigModel>>()
        )
    }
    factory {
        ConfigsRemoteProvider(
            get(named(MAPPER_CONFIGS)),
            get<ApiInterface>()
        )
    }
    viewModel { AppDataViewModel(get(named(DATA_SOURCE_CONFIGS))) }
}