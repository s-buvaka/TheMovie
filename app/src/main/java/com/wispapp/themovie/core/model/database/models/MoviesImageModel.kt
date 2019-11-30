package com.wispapp.themovie.core.model.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wispapp.themovie.core.model.database.converters.ImagesConverter

@Entity(tableName = "movie_images")
@TypeConverters(ImagesConverter::class)
data class MovieImageModel(
    @PrimaryKey(autoGenerate = false) val id: Int,
    @ColumnInfo(name = "backdrops") val backdrops: List<ImageModel>,
    @ColumnInfo(name = "posters") val posters: List<ImageModel>
)

data class ImageModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "aspect_ratio") val aspectRatio: Double,
    @ColumnInfo(name = "file_path") val filePath: String,
    @ColumnInfo(name = "iso_639_1") val iso6391: String?,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "vote_count") val voteCount: Int,
    @ColumnInfo(name = "width") val width: Int,
    @ColumnInfo(name = "height") val height: Int
)