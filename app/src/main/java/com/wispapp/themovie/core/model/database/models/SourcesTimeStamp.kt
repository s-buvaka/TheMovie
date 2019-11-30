package com.wispapp.themovie.core.model.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "source_timestamp")
data class SourcesTimeStamp(
    @PrimaryKey val timeStamp: Long,
    @ColumnInfo(name = "source_type") val type: String
)

enum class SourceType {
    CONFIG, MOVIES_OVERVIEW, MOVIES_DETAILS, MOVIE_IMAGES
}