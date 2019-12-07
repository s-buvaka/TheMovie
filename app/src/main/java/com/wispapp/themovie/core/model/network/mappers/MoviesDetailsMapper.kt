package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.*
import com.wispapp.themovie.core.model.network.models.*

class MoviesDetailsMapper(
    private val genresMapper: Mapper<GenresItemResponse, GenresItemModel>,
    private val companiesMapper: Mapper<ProductionCompaniesItemResponse, ProductionCompaniesItemModel>,
    private val countriesMapper: Mapper<ProductionCountriesItemResponse, ProductionCountriesItemModel>,
    private val languagesMapper: Mapper<SpokenLanguagesItemResponse, SpokenLanguagesItemModel>
) : Mapper<MovieDetailsResponse, MovieDetailsModel> {

    override fun mapFrom(source: MovieDetailsResponse): MovieDetailsModel =
        MovieDetailsModel(
            id = source.id,
            adult = source.adult,
            backdropPath = source.backdropPath,
            //belongsToCollection = source.belongsToCollection,
            budget = source.budget,
            genres = source.genres.map { genresMapper.mapFrom(it) },
            homepage = source.homepage ?: "",
            imdbId = source.imdbId,
            originalLanguage = source.originalLanguage,
            originalTitle = source.originalTitle,
            overview = source.overview,
            popularity = source.popularity,
            posterPath = source.posterPath,
            productionCompanies = source.productionCompanies.map { companiesMapper.mapFrom(it) },
            productionCountries = source.productionCountries.map { countriesMapper.mapFrom(it) },
            releaseDate = source.releaseDate,
            revenue = source.revenue,
            runtime = source.runtime,
            spokenLanguages = source.spokenLanguages.map { languagesMapper.mapFrom(it) },
            status = source.status,
            tagLine = source.tagLine,
            title = source.title,
            hasVideo = source.video,
            voteAverage = source.voteAverage,
            voteCount = source.voteCount
        )
}

class GenresMapper : Mapper<GenresItemResponse, GenresItemModel> {

    override fun mapFrom(source: GenresItemResponse): GenresItemModel =
        GenresItemModel(id = source.id, name = source.name)
}

class ProductionCompaniesMapper :
    Mapper<ProductionCompaniesItemResponse, ProductionCompaniesItemModel> {

    override fun mapFrom(source: ProductionCompaniesItemResponse): ProductionCompaniesItemModel =
        ProductionCompaniesItemModel(
            id = source.id,
            logoPath = source.logoPath,
            name = source.name,
            originCountry = source.originCountry
        )
}

class ProductionCountriesMapper :
    Mapper<ProductionCountriesItemResponse, ProductionCountriesItemModel> {

    override fun mapFrom(source: ProductionCountriesItemResponse): ProductionCountriesItemModel =
        ProductionCountriesItemModel(isoCode = source.isoCode, name = source.name)
}

class SpokenLanguagesMapper : Mapper<SpokenLanguagesItemResponse, SpokenLanguagesItemModel> {

    override fun mapFrom(source: SpokenLanguagesItemResponse): SpokenLanguagesItemModel =
        SpokenLanguagesItemModel(isoCode = source.isoCode, name = source.name)
}