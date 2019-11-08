//package com.wispapp.themovie.core.database
//
//import androidx.room.Insert
//import androidx.room.Query
//import com.wispapp.themovie.core.database.model.ConfigModel
//
//interface SourceDao<SOURCE> {
//
//    fun getAll(): List<SOURCE>
//
//    fun getById(id: Int): SOURCE
//
//    @Insert
//    fun insertAll(source: List<SOURCE>)
//
//    @Insert
//    fun insert(source: SOURCE)
//
//    fun deleteAll()
//
//    fun deleteById(id: Int)
//}
//
//interface ConfigDao : SourceDao<ConfigModel> {
//
//    @Query("SELECT * FROM configs")
//    override fun getAll(): List<ConfigModel>
//
//    @Query("DELETE FROM configs")
//    override fun deleteAll()
//}