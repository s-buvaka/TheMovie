package com.wispapp.themovie.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wispapp.themovie.core.database.model.ConfigModel

@Database(
    entities = [
        ConfigModel::class],
    version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun configDao(): ConfigDao
}