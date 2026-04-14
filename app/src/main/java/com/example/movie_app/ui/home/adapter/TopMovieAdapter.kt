package com.example.movie_app.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.databinding.ItemMovieBinding
import com.example.movie_app.ui.home.model.TopMovieItem

class TopMovieAdapter(
    private val listMovie: List<TopMovieItem>,
    private val onItemClick: (TopMovieItem) -> Unit
) :
    RecyclerView.Adapter<TopMovieAdapter.TopMovieViewHolder>() {

    class TopMovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopMovieViewHolder, position: Int) {

        val movie = listMovie[position]
        holder.binding.imgTopMovie.setImageResource(movie.imageId)

        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int = listMovie.size


}