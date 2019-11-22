package com.wispapp.themovie.core.model.database.converters

import com.google.gson.reflect.TypeToken
import com.wispapp.themovie.core.model.database.models.*
import java.lang.reflect.Type

class StringListConverter : TypeConverter<List<String>> {

    override val clazz: Type
        get() = object : TypeToken<List<String>>() {}.type
}

class IntListConverter : TypeConverter<List<Int>> {

    override val clazz: Type
        get() = object : TypeToken<List<Int>>() {}.type
}

class ImagesConfigConverter : TypeConverter<ImagesConfigModel> {

    override val clazz: Type
        get() = object : TypeToken<ImagesConfigModel>() {}.type
}

class GenresConverter : TypeConverter<List<GenresItemModel>> {

    override val clazz: Type
        get() = object : TypeToken<List<GenresItemModel>>() {}.type
}

class CompaniesConverter : TypeConverter<List<ProductionCompaniesItemModel>> {

    override val clazz: Type
        get() = object : TypeToken<List<ProductionCompaniesItemModel>>() {}.type
}

class CountriesConverter : TypeConverter<List<ProductionCountriesItemModel>> {

    override val clazz: Type
        get() = object : TypeToken<List<ProductionCountriesItemModel>>() {}.type
}

class LanguagesConverter : TypeConverter<List<SpokenLanguagesItemModel>> {

    override val clazz: Type
        get() = object : TypeToken<List<SpokenLanguagesItemModel>>() {}.type
}

class CategoryConverter : TypeConverter<MutableSet<CATEGORY>> {

    override val clazz: Type
        get() = object : TypeToken<MutableSet<CATEGORY>>() {}.type
}

