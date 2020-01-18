package com.wispapp.themovie.core.model.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
    @ColumnInfo(name = "adult") val adult: Boolean,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String?,
    @ColumnInfo(name = "budget") val budget: Int,
    @ColumnInfo(name = "genres") val genres: List<GenresItemModel>,
    @ColumnInfo(name = "homepage") val homepage: String,
    @ColumnInfo(name = "imdb_id") val imdbId: String,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "production_companies") val companies: List<ProductionCompaniesItemModel>,
    @ColumnInfo(name = "production_countries") val countries: List<ProductionCountriesItemModel>,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "revenue") val revenue: Int,
    @ColumnInfo(name = "runtime") val runtime: Int,
    @ColumnInfo(name = "spoken_languages") val spokenLanguages: List<SpokenLanguagesItemModel>,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "tagline") val tagLine: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "video") val hasVideo: Boolean,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int
)

data class GenresItemModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "name") val name: String
)

data class ProductionCompaniesItemModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "logo_path") val logoPath: String?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "origin_country") val originCountry: String
)

data class ProductionCountriesItemModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "iso_3166_1") val isoCode: String,
    @ColumnInfo(name = "name") val name: String
)

data class SpokenLanguagesItemModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "iso_639_1") val isoCode: String,
    @ColumnInfo(name = "name") val name: String
)
