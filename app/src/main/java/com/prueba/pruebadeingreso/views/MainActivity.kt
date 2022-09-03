package com.prueba.pruebadeingreso.views

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.prueba.pruebadeingreso.adapters.UserAdapter
import com.prueba.pruebadeingreso.databinding.ActivityMainBinding
import com.prueba.pruebadeingreso.db.Instancia
import com.prueba.pruebadeingreso.viewmodels.UserViewModel
import com.prueba.pruebadeingreso.viewmodels.UserViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val userViewModel: UserViewModel by viewModels {
        UserViewModelFactory((application as Instancia).repository, application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = UserAdapter()
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.listado.layoutManager = layoutManager
        binding.listado.setHasFixedSize(true)
        binding.listado.adapter = adapter

        //userViewModel.validarData(this)
        userViewModel.getUsuarios().observe(this) { item ->
            if(item.isEmpty())  binding.tvMensaje.visibility = View.VISIBLE
            else binding.tvMensaje.visibility = View.GONE
            adapter.replaceItems(item, this)
        }

        binding.filledTextField.editText?.addTextChangedListener {it ->
            userViewModel.searchUser(it.toString())
        }
    }

}