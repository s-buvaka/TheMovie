package com.wispapp.themovie.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wispapp.themovie.core.network.model.configs.Images

@Entity(tableName = "configs")
data class ConfigModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "config_image") val images: Images,
    @ColumnInfo(name = "config_change_keys") val changeKeys: List<String>
)

@Entity(tableName = "image_configs")
data class ImagesModel(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "base_url") val baseUrl: String,
    @ColumnInfo(name = "secure_base_url") val secureBaseUrl: String,
    @ColumnInfo(name = "poster_sizes") val posterSizes: List<String>,
    @ColumnInfo(name = "backdrop_sizes") val backdropSizes: List<String>,
    @ColumnInfo(name = "logo_sizes") val logoSizes: List<String>,
    @ColumnInfo(name = "still_sizes") val stillSizes: List<String>,
    @ColumnInfo(name = "profile_sizes") val profileSizes: List<String>
)