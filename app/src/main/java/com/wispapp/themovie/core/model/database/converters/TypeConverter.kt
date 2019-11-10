package com.wispapp.themovie.core.model.database.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface TypeConverter<T> {

    @TypeConverter
    fun fromType(model: T): String =
        Gson().toJson(model)

    @TypeConverter
    fun fromString(value: String): T {
        val listType = object : TypeToken<T>() {}.type
        return Gson().fromJson<T>(value, listType)
    }
}