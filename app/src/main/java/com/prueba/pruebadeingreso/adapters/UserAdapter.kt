package com.prueba.pruebadeingreso.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prueba.pruebadeingreso.R
import com.prueba.pruebadeingreso.databinding.ItemUsuaioBinding
import com.prueba.pruebadeingreso.entities.User
import com.prueba.pruebadeingreso.utilities.idUsuario
import com.prueba.pruebadeingreso.views.UserDetail

class UserAdapter() : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var items = listOf<User>()
    private lateinit var contexto : Context
    private lateinit var binding : ItemUsuaioBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemUsuaioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(user: User){

            val tvNombre = itemView.findViewById<TextView>(R.id.tvNombre)
            val tvTelefono = itemView.findViewById<TextView>(R.id.tvTelefono)
            val tvCorreo = itemView.findViewById<TextView>(R.id.tvCorreo)
            val tvVer = itemView.findViewById<TextView>(R.id.tvVer)

            tvNombre.text = user.name
            tvTelefono.text = user.phone
            tvCorreo.text = user.email

            tvVer.setOnClickListener {
                val itent = Intent(itemView.context, UserDetail::class.java)
                itent.putExtra(idUsuario, user.id)
                (itemView.context as Activity).startActivity(itent)
            }

        }
    }



    fun replaceItems(items: List<User>, context: Context) {
        this.items = items
        this.contexto = context
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

}