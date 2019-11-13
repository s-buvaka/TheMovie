package com.wispapp.themovie.core.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieOverviewModel
import com.wispapp.themovie.core.model.database.models.SourcesTimeStamp
import io.reactivex.Completable
import io.reactivex.Maybe

interface SourceDatabase<SOURCE> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(source: List<SOURCE>)

    fun getAll(): List<SOURCE>

    fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(source: SOURCE)

    fun getById(id: Int): SOURCE

    fun deleteById(id: Int)

    @Insert
    fun setTimestamp(timestampModel: SourcesTimeStamp)

    @Query("SELECT timeStamp FROM source_timestamp WHERE source_type Like :type")
    fun getTimestamp(type: String): Long?

    @Query("DELETE FROM source_timestamp WHERE source_type Like :type")
    fun delTimestamp(type: String)
}

@Dao
interface ConfigDao : SourceDatabase<ConfigModel> {

    @Query("SELECT * FROM configs")
    override fun getAll(): List<ConfigModel>

    @Query("DELETE FROM configs")
    override fun deleteAll()

    @Query("SELECT * FROM configs WHERE id=:id")
    override fun getById(id: Int): ConfigModel

    @Query("DELETE FROM configs WHERE id=:id")
    override fun deleteById(id: Int)
}

@Dao
interface MoviesOverviewDao : SourceDatabase<MovieOverviewModel> {

    @Query("SELECT * FROM movies_overview")
    override fun getAll(): List<MovieOverviewModel>

    @Query("DELETE FROM movies_overview")
    override fun deleteAll()

    @Query("SELECT * FROM movies_overview WHERE id=:id")
    override fun getById(id: Int): MovieOverviewModel

    @Query("DELETE FROM movies_overview WHERE id=:id")
    override fun deleteById(id: Int)
}