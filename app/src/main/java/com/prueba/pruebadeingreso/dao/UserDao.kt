package com.prueba.pruebadeingreso.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.prueba.pruebadeingreso.entities.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<User>)

    @Query("SELECT * FROM user order by id ")
    fun getAll(): Flow<List<User>>

    @Query("SELECT count(*) as cantidad FROM user ")
    suspend fun getCountUser(): Int

    @Query("SELECT * FROM user where name LIKE '%'||:name||'%' order by id ")
    suspend fun getUsuarioName(name:String?): List<User>

    @Query("SELECT * FROM user where id = :idUsuario ")
    suspend fun getUser(idUsuario:Int): User


}