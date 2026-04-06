package com.example.movie_app.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie_app.Model.MovieIntro
import com.example.movie_app.databinding.ItemMovieIntroSideBinding
import com.example.movie_app.databinding.ItemMovieIntroMidBinding

class IntroAdapter(private val list: List<MovieIntro>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object { // giống như staitc trong java
        const val TYPE_MID = 0
        const val TYPE_SIDE = 1
    }

    // viewHolder cho item giữa (nhỏ)
    class MidViewHolder(val binding: ItemMovieIntroMidBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SideViewHolder(val binding: ItemMovieIntroSideBinding) :
        RecyclerView.ViewHolder(binding.root)

    //    bind dữ liệu vào ui
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_MID) {
            //inflate layout item
            val binding = ItemMovieIntroMidBinding.inflate(inflater, parent, false)
            MidViewHolder(binding)
        } else {

            val binding = ItemMovieIntroSideBinding.inflate(inflater, parent, false)
            SideViewHolder(binding)
        }
    }

    //    đưa vào ui => như là mỗi item mình đã định nghĩa thôi
    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder, position: Int
    ) {
        val item = list[position]
        when (holder) {
            is MidViewHolder -> {
                holder.binding.imgPoster.setImageResource(item.image)

            }
            is SideViewHolder -> {
                holder.binding.imgPoster.setImageResource(item.image)

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].isMid) TYPE_MID else TYPE_SIDE
    }
}