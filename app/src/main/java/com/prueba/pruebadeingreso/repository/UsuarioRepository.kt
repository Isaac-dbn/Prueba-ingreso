package com.prueba.pruebadeingreso.repository

import android.content.Context
import android.widget.Toast
import androidx.annotation.WorkerThread
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.prueba.pruebadeingreso.api.confi
import com.prueba.pruebadeingreso.api.iconfi
import com.prueba.pruebadeingreso.dao.UserDao
import com.prueba.pruebadeingreso.entities.User
import com.prueba.pruebadeingreso.utilities.Dialogos
import com.prueba.pruebadeingreso.utilities.Network
import com.prueba.pruebadeingreso.utilities.uri_users
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class UsuarioRepository(private val userDao: UserDao) {

    var allUsers: Flow<List<User>> = userDao.getAll()

    suspend fun getUser(idUser:Int) : User {
        return userDao.getUser(idUser)
    }

    suspend fun getCountUser() : Int{
        return userDao.getCountUser()
    }

    @WorkerThread
    suspend fun insert( user: List<User>) {
        userDao.insertAll(user)
    }

    fun getUsuarioApi(contexto: Context, progres:SweetAlertDialog){
        confi.getData(uri_users, contexto, object : iconfi {

            override fun onResponse(response: JSONArray?) {
                val usuarios : MutableList<User> = mutableListOf()
                for (i in 0 until response!!.length()) {
                    val item = response.getJSONObject(i)
                    item.put("address", item.getString("address"))
                    item.put("company", item.getString("company"))
                    usuarios.add(Gson().fromJson(item.toString(), User::class.java))
                }
                CoroutineScope(SupervisorJob()).launch {
                    insert(usuarios)
                    progres.dismissWithAnimation()
                }
            }

            override fun onError(error: VolleyError?) {
                progres.dismissWithAnimation()
                Toast.makeText(contexto, "Inconvenientes al consultar el listado de usuarios", Toast.LENGTH_LONG).show()
            }
        })
    }

    suspend fun searchUser(name:String?) : List<User>{
        return userDao.getUsuarioName(name)
    }

}
