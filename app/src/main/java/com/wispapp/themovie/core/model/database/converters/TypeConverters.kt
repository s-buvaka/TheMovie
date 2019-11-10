package com.wispapp.themovie.core.model.database.converters

import com.wispapp.themovie.core.model.database.models.ImagesConfigModel

class ImagesConfigConverter : TypeConverter<ImagesConfigModel>

class StringListConverter : TypeConverter<List<String>>

class IntListConverter : TypeConverter<List<Int>>