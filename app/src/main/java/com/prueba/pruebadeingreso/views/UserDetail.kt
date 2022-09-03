package com.prueba.pruebadeingreso.views

import android.R
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba.pruebadeingreso.adapters.PostAdapter
import com.prueba.pruebadeingreso.databinding.ActivityUserDetailBinding
import com.prueba.pruebadeingreso.db.Instancia
import com.prueba.pruebadeingreso.utilities.idUsuario
import com.prueba.pruebadeingreso.utilities.titulo_detail
import com.prueba.pruebadeingreso.viewmodels.PostViewModel
import com.prueba.pruebadeingreso.viewmodels.PostViewModelFactory

class UserDetail : AppCompatActivity() {

    private lateinit var binding: ActivityUserDetailBinding
    private var idUser : Int = 0

    private val postViewModel: PostViewModel by viewModels {
        PostViewModelFactory((application as Instancia).postRepository, (application as Instancia).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = titulo_detail

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.listado.layoutManager = layoutManager
        binding.listado.setHasFixedSize(true)
        val adapter = PostAdapter()
        binding.listado.adapter = adapter

        idUser = intent.getIntExtra(idUsuario, 0)
        postViewModel.consultarPost(this, idUser, binding).observe(this){ datos ->
            if(datos.isEmpty())  binding.tvMensaje.visibility = View.VISIBLE
            else binding.tvMensaje.visibility = View.GONE
            adapter.replaceItems(datos)
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}