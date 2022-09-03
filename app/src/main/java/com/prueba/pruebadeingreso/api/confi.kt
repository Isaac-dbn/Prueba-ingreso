package com.prueba.pruebadeingreso.api

import android.content.Context
import com.android.volley.*
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.prueba.pruebadeingreso.utilities.url_base
import org.json.JSONArray


class confi {

    companion object {
        fun getData(
            ruta: String?,
            contexto: Context?,
            callbacks: iconfi
        ) {
            val tiempoEspera: Int = 6000

            val requstQueue: RequestQueue = Volley.newRequestQueue(contexto)
            val jsonobj = JsonArrayRequest(Request.Method.GET, url_base+ruta, JSONArray(),
                { response -> callbacks.onResponse(response) },
                { error -> callbacks.onError(error) }
            )
            jsonobj.retryPolicy = DefaultRetryPolicy(
                tiempoEspera, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            requstQueue.add(jsonobj)
        }
    }

}