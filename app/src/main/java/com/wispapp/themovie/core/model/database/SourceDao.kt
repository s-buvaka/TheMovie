package com.wispapp.themovie.core.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wispapp.themovie.core.model.database.models.ConfigModel
import com.wispapp.themovie.core.model.database.models.MovieDetailsModel
import com.wispapp.themovie.core.model.database.models.PopularsMovieModel
import com.wispapp.themovie.core.model.database.models.SourcesTimeStamp

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
interface MoviesOverviewDao : SourceDatabase<PopularsMovieModel> {

    @Query("SELECT * FROM movies_overview")
    override fun getAll(): List<PopularsMovieModel>

    @Query("DELETE FROM movies_overview")
    override fun deleteAll()

    @Query("SELECT * FROM movies_overview WHERE id=:id")
    override fun getById(id: Int): PopularsMovieModel

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