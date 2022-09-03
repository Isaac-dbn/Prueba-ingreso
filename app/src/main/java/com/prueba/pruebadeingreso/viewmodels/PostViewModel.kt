package com.prueba.pruebadeingreso.viewmodels

import android.content.Context
import androidx.lifecycle.*
import com.prueba.pruebadeingreso.databinding.ActivityUserDetailBinding
import com.prueba.pruebadeingreso.entities.Post
import com.prueba.pruebadeingreso.repository.PostRepository
import com.prueba.pruebadeingreso.repository.UsuarioRepository
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository, private val userRepository: UsuarioRepository) : ViewModel()  {

    fun consultarPost(contexto:Context, idUser:Int, binding: ActivityUserDetailBinding) : LiveData<List<Post>>  {
        viewModelScope.launch {
            repository.validarDatos(contexto, idUser)
            val user = userRepository.getUser(idUser)
            binding.tvNombre.text = user.name
            binding.tvTelefono.text = user.phone
            binding.tvCorreo.text = user.email
        }
        return repository.consultarPost(idUser).asLiveData()
    }

}

class PostViewModelFactory(private val repository: PostRepository, private val userRepository: UsuarioRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PostViewModel(repository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}