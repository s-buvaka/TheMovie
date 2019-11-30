package com.wispapp.themovie.core.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wispapp.themovie.core.model.database.models.*

interface SourceDatabase<SOURCE> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(source: List<SOURCE>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(source: SOURCE)

    fun getAll(): List<SOURCE>

    fun getById(id: Int): SOURCE

    fun deleteAll()

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
interface MoviesDao : SourceDatabase<MovieModel> {

    @Query("SELECT * FROM movies_overview")
    override fun getAll(): List<MovieModel>

    @Query("SELECT * FROM movies_overview WHERE id=:id")
    override fun getById(id: Int): MovieModel

    @Query("DELETE FROM movies_overview")
    override fun deleteAll()

    @Query("DELETE FROM movies_overview WHERE id=:id")
    override fun deleteById(id: Int)
}

@Dao
interface MovieDetailsDao : SourceDatabase<MovieDetailsModel> {

    @Query("SELECT * FROM movie_details")
    override fun getAll(): List<MovieDetailsModel>

    @Query("DELETE FROM movie_details")
    override fun deleteAll()

    @Query("SELECT * FROM movie_details WHERE id=:id")
    override fun getById(id: Int): MovieDetailsModel

    @Query("DELETE FROM movie_details WHERE id=:id")
    override fun deleteById(id: Int)
}

@Dao
interface MovieImagesDao : SourceDatabase<MovieImageModel> {

    @Query("SELECT * FROM movie_images")
    override fun getAll(): List<MovieImageModel>

    @Query("DELETE FROM movie_images")
    override fun deleteAll()

    @Query("SELECT * FROM movie_images WHERE id=:id")
    override fun getById(id: Int): MovieImageModel

    @Query("DELETE FROM movie_images WHERE id=:id")
    override fun deleteById(id: Int)
}