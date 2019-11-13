package com.wispapp.themovie.core.model.network.models

import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    @SerializedName("belongs_to_collection") val belongsToCollection: Any,//TODO Понять какой тут объект приходит
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<GenresItemResponse>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompaniesItemResponse>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountriesItemResponse>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguagesItemResponse>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagLine: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

data class GenresItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

data class ProductionCompaniesItemResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class ProductionCountriesItemResponse(
    @SerializedName("iso_3166_1") val isoCode: String,
    @SerializedName("name") val name: String
)

data class SpokenLanguagesItemResponse(
    @SerializedName("iso_639_1") val isoCode: String,
    @SerializedName("name") val name: String
)


