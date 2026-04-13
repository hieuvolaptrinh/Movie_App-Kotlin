package com.example.movie_app.ui.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.databinding.ItemMovieBinding

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
//        val params =
//            holder.itemView.layoutParams as RecyclerView.LayoutParams // để lấy item ra, tự thêm tay phần marign cho nó trừ phần tử cuối
//        if (position == listMovie.size - 1) {
//            params.marginEnd = 0
//        } else {
//            params.marginEnd = 10.dp
//        }
        val movie = listMovie[position]
        holder.binding.imgTopMovie.setImageResource(movie.imageId)

        holder.itemView.setOnClickListener {
            onItemClick(movie)
        }
    }

    override fun getItemCount(): Int = listMovie.size

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).toInt()
}