package com.prueba.pruebadeingreso.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prueba.pruebadeingreso.R
import com.prueba.pruebadeingreso.databinding.ItemPostBinding
import com.prueba.pruebadeingreso.entities.Post

class PostAdapter() : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    private var items = listOf<Post>()
    private lateinit var binding : ItemPostBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        fun bindItems(post: Post){

            val tvTitulo = itemView.findViewById<TextView>(R.id.tvTitulo)
            val tvDescripcion = itemView.findViewById<TextView>(R.id.tvDescripcion)

            tvTitulo.text = post.title
            tvDescripcion.text = post.body

        }
    }



    fun replaceItems(items: List<Post>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

}