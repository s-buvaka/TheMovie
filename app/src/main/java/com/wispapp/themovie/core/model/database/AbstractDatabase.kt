package com.wispapp.themovie.core.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wispapp.themovie.core.model.database.models.ConfigModel

@Database(
    entities = [
        ConfigModel::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getConfigDao(): ConfigDao
}