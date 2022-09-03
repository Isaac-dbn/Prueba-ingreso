package com.prueba.pruebadeingreso.api

import com.android.volley.VolleyError
import org.json.JSONArray
import org.json.JSONObject

interface iconfi {

    fun onResponse(response: JSONArray?)
    fun onError(error: VolleyError?)

}