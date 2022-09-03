package com.prueba.pruebadeingreso.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prueba.pruebadeingreso.entities.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(post: List<Post>)

    @Query("SELECT * FROM post where userId = :idUser order by id ")
    fun getAll(idUser:Int): Flow<List<Post>>

    @Query("SELECT count(*) as cantidad FROM post where userId = :idUser  ")
    suspend fun getcount(idUser:Int): Int

}