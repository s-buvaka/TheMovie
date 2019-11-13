package com.wispapp.themovie.core.model.network.mappers

import com.wispapp.themovie.core.common.Mapper
import com.wispapp.themovie.core.model.database.models.*
import com.wispapp.themovie.core.model.network.models.*

class MoviesDetailsMapper : Mapper<MovieDetailsResponse, MovieDetailsModel> {

    override fun mapFrom(source: MovieDetailsResponse): MovieDetailsModel =
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//        MovieDetailsModel(
//            adult = source.adult,
//            backdropPath = source.backdropPath,
//            belongsToCollection = source.belongsToCollection,
//            budget = source.budget,
//            genres = source.genres
//        )
}

class GenresItemMapper : Mapper<GenresItemResponse, GenresItemModel> {

    override fun mapFrom(source: GenresItemResponse): GenresItemModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class ProductionCompaniesItemMapper :
    Mapper<ProductionCompaniesItemResponse, ProductionCompaniesItemModel> {

    override fun mapFrom(source: ProductionCompaniesItemResponse): ProductionCompaniesItemModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class ProductionCountriesItemMapper :
    Mapper<ProductionCountriesItemResponse, ProductionCountriesItemModel> {

    override fun mapFrom(source: ProductionCountriesItemResponse): ProductionCountriesItemModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

class SpokenLanguagesItemMapper : Mapper<SpokenLanguagesItemResponse, SpokenLanguagesItemModel> {

    override fun mapFrom(source: SpokenLanguagesItemResponse): SpokenLanguagesItemModel {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}