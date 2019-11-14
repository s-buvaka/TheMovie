package com.wispapp.themovie.core.model.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.wispapp.themovie.core.model.database.converters.CompaniesConverter
import com.wispapp.themovie.core.model.database.converters.CountriesConverter
import com.wispapp.themovie.core.model.database.converters.GenresConverter
import com.wispapp.themovie.core.model.database.converters.LanguagesConverter

@Entity(tableName = "movie_details")
@TypeConverters(
    GenresConverter::class,
    CompaniesConverter::class,
    CountriesConverter::class,
    LanguagesConverter::class
)
data class MovieDetailsModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String,
    //@SerializedName("belongs_to_collection") val belongsToCollection: Any,//TODO Понять какой тут объект приходит
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<GenresItemModel>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompaniesItemModel>,
    @SerializedName("production_countries") val productionCountries: List<ProductionCountriesItemModel>,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("spoken_languages") val spokenLanguages: List<SpokenLanguagesItemModel>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagLine: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)

data class GenresItemModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String
)

data class ProductionCompaniesItemModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("name") val name: String,
    @SerializedName("origin_country") val originCountry: String
)

data class ProductionCountriesItemModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("iso_3166_1") val isoCode: String,
    @SerializedName("name") val name: String
)

data class SpokenLanguagesItemModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @SerializedName("iso_639_1") val isoCode: String,
    @SerializedName("name") val name: String
)
