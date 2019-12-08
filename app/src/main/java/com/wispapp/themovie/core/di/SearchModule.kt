@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.datasource.SearchMovieDataSource
import com.wispapp.themovie.core.model.network.ApiInterface
import com.wispapp.themovie.core.model.network.SearchMovieProvider
import com.wispapp.themovie.core.model.network.mappers.MovieResultMapper
import com.wispapp.themovie.core.model.network.mappers.MoviesMapper
import com.wispapp.themovie.ui.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val MAPPER_MOVIES_RESULT_NON_CATEGORY = "mapper_movies_result_non_category"
private const val MAPPER_MOVIES_NON_CATEGORY = "mapper_movies_non_category"

private const val NETWORK_PROVIDER_SEARCH_MOVIE = "network_provider_search_movie"

private const val DATA_SOURCE_SEARCH_MOVIE = "data_source_search_movie"

val searchModule = module {

    single(named(MAPPER_MOVIES_NON_CATEGORY)) { MoviesMapper(CATEGORY.NON_CATEGORY) }

    single(named(MAPPER_MOVIES_RESULT_NON_CATEGORY)) {
        MovieResultMapper(get(named(MAPPER_MOVIES_NON_CATEGORY)))
    }

    factory(named(NETWORK_PROVIDER_SEARCH_MOVIE)) {
        SearchMovieProvider(
            get(named(MAPPER_MOVIES_RESULT_NON_CATEGORY)),
            get<ApiInterface>()
        )
    }

    factory(named(DATA_SOURCE_SEARCH_MOVIE)) {
        SearchMovieDataSource(get(named(NETWORK_PROVIDER_SEARCH_MOVIE)))
    }

    viewModel { SearchViewModel(get(named(DATA_SOURCE_SEARCH_MOVIE))) }
}