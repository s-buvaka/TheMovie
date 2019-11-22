@file:Suppress("RemoveExplicitTypeArguments")

package com.wispapp.themovie.core.di

import com.wispapp.themovie.core.application.Constants
import com.wispapp.themovie.core.common.ImageLoader
import com.wispapp.themovie.core.model.cache.DataBaseSourceCacheProvider
import com.wispapp.themovie.core.model.cache.TimeoutCachePolicyImpl
import com.wispapp.themovie.core.model.database.MovieDetailsDao
import com.wispapp.themovie.core.model.database.MoviesDao
import com.wispapp.themovie.core.model.database.models.CATEGORY
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.MovieModel
import com.wispapp.themovie.core.model.database.models.SourceType
import com.wispapp.themovie.core.model.datasource.MovieDetailsDataSource
import com.wispapp.themovie.core.model.datasource.MoviesDataSource
import com.wispapp.themovie.core.model.network.*
import com.wispapp.themovie.core.model.network.mappers.*
import com.wispapp.themovie.core.viewmodel.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val MAPPER_MOVIES_RESULT_NOW_PLAYING = "mapper_movies_result_now_playing"
private const val MAPPER_MOVIES_RESULT_POPULAR = "mapper_movies_result_popular"
private const val MAPPER_MOVIES_RESULT_TOP_RATED = "mapper_movies_result_top_rated"
private const val MAPPER_MOVIES_RESULT_UPCOMING = "mapper_movies_result_upcoming"

private const val MAPPER_MOVIES_NOW_PLAYING = "mapper_movies_now_playing"
private const val MAPPER_MOVIES_POPULAR = "mapper_movies_popular"
private const val MAPPER_MOVIES_TOP_RATED = "mapper_movies_top_rated"
private const val MAPPER_MOVIES_UPCOMING = "mapper_movies_upcoming"

private const val MAPPER_MOVIES_DETAILS = "mapper_movies_details"
private const val MAPPER_MOVIES_GENRES = "mapper_movies_genres"
private const val MAPPER_MOVIES_COMPANIES = "mapper_movies_companies"
private const val MAPPER_MOVIES_COUNTRIES = "mapper_movies_countries"
private const val MAPPER_MOVIES_LANGUAGE = "mapper_movies_language"

private const val NETWORK_PROVIDER_NOW_PLAYING_MOVIES = "network_provider_now_playing_movies"
private const val NETWORK_PROVIDER_POPULAR_MOVIES = "network_provider_popular_movies"
private const val NETWORK_PROVIDER_TOP_RATED_MOVIES = "network_provider_top_rated_movies"
private const val NETWORK_PROVIDER_UPCOMING_MOVIES = "network_provider_upcoming_movies"

private const val NETWORK_PROVIDER_MOVIE_DETAILS = "network_provider_movie_details"

private const val DATABASE_SOURCE_MOVIES = "database_source_movies"
private const val DATABASE_SOURCE_MOVIE_DETAILS = "database_source_movie_details"

private const val DATA_SOURCE_MOVIES = "data_source_movies"

private const val DATA_SOURCE_MOVIE_DETAILS = "data_source_movie_details"

private const val CACHE_POLICY_MOVIES = "cash_policy_movies"

val moviesModule = module {

    single(named(MAPPER_MOVIES_NOW_PLAYING)) { MoviesMapper(CATEGORY.NOW_PLAYING) }
    single(named(MAPPER_MOVIES_POPULAR)) { MoviesMapper(CATEGORY.POPULAR) }
    single(named(MAPPER_MOVIES_TOP_RATED)) { MoviesMapper(CATEGORY.TOP_RATED) }
    single(named(MAPPER_MOVIES_UPCOMING)) { MoviesMapper(CATEGORY.UPCOMING) }

    single(named(MAPPER_MOVIES_RESULT_NOW_PLAYING)) { MovieResultMapper(get(named(MAPPER_MOVIES_NOW_PLAYING))) }
    single(named(MAPPER_MOVIES_RESULT_POPULAR)) { MovieResultMapper(get(named(MAPPER_MOVIES_POPULAR))) }
    single(named(MAPPER_MOVIES_RESULT_TOP_RATED)) { MovieResultMapper(get(named(MAPPER_MOVIES_TOP_RATED))) }
    single(named(MAPPER_MOVIES_RESULT_UPCOMING)) { MovieResultMapper(get(named(MAPPER_MOVIES_UPCOMING))) }

    single(named(MAPPER_MOVIES_LANGUAGE)) { SpokenLanguagesMapper() }
    single(named(MAPPER_MOVIES_COUNTRIES)) { ProductionCountriesMapper() }
    single(named(MAPPER_MOVIES_COMPANIES)) { ProductionCompaniesMapper() }
    single(named(MAPPER_MOVIES_GENRES)) { GenresMapper() }

    single(named(MAPPER_MOVIES_DETAILS)) {
        MoviesDetailsMapper(
            get(named(MAPPER_MOVIES_GENRES)),
            get(named(MAPPER_MOVIES_COMPANIES)),
            get(named(MAPPER_MOVIES_COUNTRIES)),
            get(named(MAPPER_MOVIES_LANGUAGE))
        )
    }

    factory(named(CACHE_POLICY_MOVIES)) { TimeoutCachePolicyImpl(Constants.CACHE_TIMEOUT_MOVIES_DATA) }

    factory(named(NETWORK_PROVIDER_NOW_PLAYING_MOVIES)) {
        NowPlayingMoviesRemoteProvider(
            get(named(MAPPER_MOVIES_RESULT_NOW_PLAYING)),
            get<ApiInterface>()
        )
    }

    factory(named(NETWORK_PROVIDER_POPULAR_MOVIES)) {
        PopularMoviesRemoteProvider(
            get(named(MAPPER_MOVIES_RESULT_POPULAR)),
            get<ApiInterface>()
        )
    }

    factory(named(NETWORK_PROVIDER_TOP_RATED_MOVIES)) {
        TopRatedMoviesRemoteProvider(
            get(named(MAPPER_MOVIES_RESULT_TOP_RATED)),
            get<ApiInterface>()
        )
    }

    factory(named(NETWORK_PROVIDER_UPCOMING_MOVIES)) {
        UpcomingMoviesRemoteProvider(
            get(named(MAPPER_MOVIES_RESULT_UPCOMING)),
            get<ApiInterface>()
        )
    }

    factory(named(NETWORK_PROVIDER_MOVIE_DETAILS)) {
        MoviesDetailsProvider(
            get(named(MAPPER_MOVIES_DETAILS)),
            get<ApiInterface>()
        )
    }

    factory(named(DATABASE_SOURCE_MOVIES)) {
        DataBaseSourceCacheProvider<MovieModel>(
            get(named(CACHE_POLICY_MOVIES)),
            SourceType.MOVIES_OVERVIEW,
            get<MoviesDao>()
        )
    }

    factory(named(DATABASE_SOURCE_MOVIE_DETAILS)) {
        DataBaseSourceCacheProvider<MovieDetailsModel>(
            get(named(CACHE_POLICY_MOVIES)),
            SourceType.MOVIES_DETAILS,
            get<MovieDetailsDao>()
        )
    }

    factory(named(DATA_SOURCE_MOVIES)) {
        MoviesDataSource(
            get(named(NETWORK_PROVIDER_NOW_PLAYING_MOVIES)),
            get(named(NETWORK_PROVIDER_POPULAR_MOVIES)),
            get(named(NETWORK_PROVIDER_TOP_RATED_MOVIES)),
            get(named(NETWORK_PROVIDER_UPCOMING_MOVIES)),
            get(named(DATABASE_SOURCE_MOVIES))
        )
    }

    factory(named(DATA_SOURCE_MOVIE_DETAILS)) {
        MovieDetailsDataSource(
            get(named(NETWORK_PROVIDER_MOVIE_DETAILS)),
            get(named(DATABASE_SOURCE_MOVIE_DETAILS))
        )
    }

    viewModel {
        MoviesViewModel(
            get(named(DATA_SOURCE_MOVIES)),
            get(named(DATA_SOURCE_MOVIE_DETAILS)),
            get(named(DATA_SOURCE_CONFIGS)),
            get<ImageLoader>()
        )
    }
}