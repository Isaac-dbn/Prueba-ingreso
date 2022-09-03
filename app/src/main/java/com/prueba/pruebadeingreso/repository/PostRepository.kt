package com.prueba.pruebadeingreso.repository

import android.content.Context
import android.widget.Toast
import androidx.annotation.WorkerThread
import cn.pedant.SweetAlert.SweetAlertDialog
import com.android.volley.VolleyError
import com.google.gson.Gson
import com.prueba.pruebadeingreso.api.confi
import com.prueba.pruebadeingreso.api.iconfi
import com.prueba.pruebadeingreso.dao.PostDao
import com.prueba.pruebadeingreso.entities.Post
import com.prueba.pruebadeingreso.utilities.Dialogos
import com.prueba.pruebadeingreso.utilities.Network
import com.prueba.pruebadeingreso.utilities.uri_user_post
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.json.JSONArray

class PostRepository(private val postDao: PostDao) {


    fun consultarPost(idUser: Int) : Flow<List<Post>> {
        return postDao.getAll(idUser)
    }

    suspend fun validarDatos(contexto: Context, idUser: Int) {
        if(postDao.getcount(idUser) == 0){
            if(Network.isOnline(contexto)){
                val progres = Dialogos.mostrarDialogoProgress(contexto, "Cargando información")
                progres!!.show()
                getPostApi(contexto, idUser, progres)
            }else {
                Toast.makeText(contexto, "No cuenta con conexion a internet", Toast.LENGTH_LONG).show()
            }
        }
    }

    @WorkerThread
    suspend fun insert( user: List<Post>) {
        postDao.insertAll(user)
    }

    private fun getPostApi(contexto: Context, idUser: Int, progres:SweetAlertDialog){
        confi.getData(uri_user_post+idUser, contexto, object : iconfi {

            override fun onResponse(response: JSONArray?) {
                val post : MutableList<Post> = mutableListOf()
                for (i in 0 until response!!.length()) {
                    val item = response.getJSONObject(i)
                    post.add(Gson().fromJson(item.toString(), Post::class.java))
                }
                CoroutineScope(SupervisorJob()).launch {
                    insert(post)
                    progres.dismissWithAnimation()
                }
            }

            override fun onError(error: VolleyError?) {
                progres.dismissWithAnimation()
                Toast.makeText(contexto, "Inconvenientes al consultar el listado de post", Toast.LENGTH_LONG).show()
            }
        })
    }

}