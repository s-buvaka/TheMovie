@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.application.Constants.CACHE_TIMEOUT_CONFIGS
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
import com.wispapp.themovie.core.viewmodel.ConfigsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val DATA_SOURCE_CONFIGS = "data_source_configs"

private const val MAPPER_IMAGE_CONFIGS = "mapper_image_configs"
private const val MAPPER_CONFIGS = "mapper_configs"

private const val CACHE_POLICY_CONFIGS = "default_cash_policy"

val configsModule = module {

    factory(named(MAPPER_IMAGE_CONFIGS)) { ImageConfigMapper() }
    factory(named(MAPPER_CONFIGS)) { ConfigsMapper(get(named(MAPPER_IMAGE_CONFIGS))) }

    factory(named(CACHE_POLICY_CONFIGS)) { TimeoutCachePolicyImpl(CACHE_TIMEOUT_CONFIGS) }

    factory {
        DataBaseSourceCacheProvider<ConfigModel>(
            get(named(CACHE_POLICY_CONFIGS)),
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
    viewModel { ConfigsViewModel(get(named(DATA_SOURCE_CONFIGS))) }
}