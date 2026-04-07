package com.example.movie_app.UI

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movie_app.Adapter.CasterAdapter
import com.example.movie_app.Model.TopMovie
import com.example.movie_app.R
import com.example.movie_app.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val movie = if (android.os.Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("movie", TopMovie::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<TopMovie>("movie")
        }

        movie?.let {
            binding.imageMovie.setImageResource(it.imageId)
            binding.tvMovieName.text = it.name
        }


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        initCaster()
    }

     fun initCaster() {
        val casters = listOf(
            R.drawable.cast_1, R.drawable.cast_2, R.drawable.cast_3, R.drawable.cast_4,
        )
        val adapter= CasterAdapter(casters)
        binding.rvCaster.adapter = adapter
    }
}