package com.prueba.pruebadeingreso.db

import android.app.Application
import com.prueba.pruebadeingreso.repository.PostRepository
import com.prueba.pruebadeingreso.repository.UsuarioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class Instancia : Application(){

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UsuarioRepository(database.userDao()) }
    val postRepository by lazy { PostRepository(database.postDao()) }

}
