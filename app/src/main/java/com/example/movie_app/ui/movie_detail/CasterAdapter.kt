package com.example.movie_app.ui.movie_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.databinding.ItemCastBinding

class CasterAdapter(private val listCaster: List<Int>) : RecyclerView.Adapter<CasterAdapter.CasterViewHolder>() {

    class CasterViewHolder(val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CasterViewHolder {
        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CasterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CasterViewHolder, position: Int) {
        val casterImageId = listCaster[position]
        holder.binding.imgCaster.setImageResource(casterImageId)
    }

    override fun getItemCount(): Int = listCaster.size

}