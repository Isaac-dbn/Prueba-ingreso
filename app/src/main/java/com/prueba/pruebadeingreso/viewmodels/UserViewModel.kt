package com.prueba.pruebadeingreso.viewmodels

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.*
import com.prueba.pruebadeingreso.entities.User
import com.prueba.pruebadeingreso.repository.UsuarioRepository
import com.prueba.pruebadeingreso.utilities.Dialogos
import com.prueba.pruebadeingreso.utilities.Network
import kotlinx.coroutines.launch

class UserViewModel(private val repositorio:UsuarioRepository, private val application: Application) : ViewModel() {

    lateinit var allUsers: MutableLiveData<List<User>>

    fun getUsuarios() : MutableLiveData<List<User>>{
        viewModelScope.launch{
            if(getCountUser() == 0){
                if(Network.isOnline(application.applicationContext)){
                    val progres = Dialogos.mostrarDialogoProgress(application.applicationContext, "Cargando información")
                    progres!!.show()
                    repositorio.getUsuarioApi(application.applicationContext, progres)
                }else{
                    Toast.makeText(application.applicationContext, "No cuenta con conexion a internet", Toast.LENGTH_LONG).show()
                }
            }
        }
        allUsers = repositorio.allUsers.asLiveData() as MutableLiveData<List<User>>
        return allUsers
    }

    suspend fun getCountUser() : Int {
        return repositorio.getCountUser()
    }

   /*fun validarData(contexto:Context) = viewModelScope.launch{
       repositorio.validarData(contexto)
   }*/
    fun searchUser(name:String?) = viewModelScope.launch {
        allUsers.postValue(repositorio.searchUser(name))
    }

}


class UserViewModelFactory(private val repository: UsuarioRepository, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(repository, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}