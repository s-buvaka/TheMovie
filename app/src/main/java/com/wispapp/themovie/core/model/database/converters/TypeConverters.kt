package com.wispapp.themovie.core.model.database.converters

import com.wispapp.themovie.core.model.database.models.*

class StringListConverter : TypeConverter<List<String>>

class IntListConverter : TypeConverter<List<Int>>

class ImagesConfigConverter : TypeConverter<ImagesConfigModel>

class GenresConverter : TypeConverter<List<GenresItemModel>>

class CompaniesConverter : TypeConverter<List<ProductionCompaniesItemModel>>

class CountriesConverter : TypeConverter<List<ProductionCountriesItemModel>>

class LanguagesConverter : TypeConverter<List<SpokenLanguagesItemModel>>

