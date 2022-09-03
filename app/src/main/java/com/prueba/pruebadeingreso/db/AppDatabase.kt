package com.prueba.pruebadeingreso.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prueba.pruebadeingreso.dao.PostDao
import com.prueba.pruebadeingreso.dao.UserDao
import com.prueba.pruebadeingreso.entities.Post
import com.prueba.pruebadeingreso.entities.User
import kotlinx.coroutines.CoroutineScope

@Database(entities = [
    User::class,
    Post::class
], version = 2, exportSchema = false)

abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao() : UserDao
    abstract fun postDao() : PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pruebaingreso.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}