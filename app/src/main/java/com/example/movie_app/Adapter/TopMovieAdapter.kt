package com.example.movie_app.Adapter

import android.R
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movie_app.databinding.ItemMovieBinding

class TopMovieAdapter(private val list: List<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class TopMovieViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    // như cái khung hiển thị thôi
    override fun onCreateViewHolder(
        p0: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val binding = ItemMovieBinding.inflate(inflater, parent, false)
        return TopMovieViewHolder(binding)
    }

    //đưa dữa liệu vào trong ui
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = list[position]
        when (holder) {
            is TopMovieViewHolder -> {
                holder.binding.imgTopMovie.setImageResource(item.toInt())
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}