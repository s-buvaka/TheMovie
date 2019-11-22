package com.wispapp.themovie.core.model.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wispapp.themovie.core.model.database.converters.CategoryConverter
import com.wispapp.themovie.core.model.database.converters.IntListConverter

data class MoviesResultModel(
    val results: List<MovieModel>
)

@Entity(tableName = "movies_overview")
@TypeConverters(IntListConverter::class, CategoryConverter::class)
data class MovieModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "original_title") val originalTitle: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "popularity") val popularity: Double,
    @ColumnInfo(name = "original_language") val originalLanguage: String,
    @ColumnInfo(name = "video") val hasVideo: Boolean = false,
    @ColumnInfo(name = "genre_ids") val genreIds: List<Int> = emptyList(),
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "backdrop_path") val backdropPath: String,
    @ColumnInfo(name = "release_date") val releaseDate: String,
    @ColumnInfo(name = "adult") val isAdult: Boolean = false,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "category") val category: MutableSet<CATEGORY>
)

enum class CATEGORY { NOW_PLAYING, POPULAR, TOP_RATED, UPCOMING }