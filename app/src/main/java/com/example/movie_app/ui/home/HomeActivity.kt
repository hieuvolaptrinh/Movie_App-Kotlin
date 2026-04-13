package com.example.movie_app.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app.R
import com.example.movie_app.databinding.ActivityHomeBinding
import com.example.movie_app.ui.movie_detail.MovieDetailActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        initPopularMovie()
        initTodayMovie()

    }



    private fun initPopularMovie() {
        binding.tvPopularTitle.text = getString(R.string.popular_movies)
        val popularList = listOf(
            TopMovieItem(R.drawable.popular_1, "Black Widow"),
            TopMovieItem(R.drawable.popular_2, "Tenet"),
            TopMovieItem(R.drawable.popular_3, "Bell Bottom")
        )
        val adapter = TopMovieAdapter(popularList) { item ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra("movie", item)
            startActivity(intent)

        }

        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = adapter
    }

    private fun initTodayMovie() {
        val todayList = listOf(
            TopMovieItem(R.drawable.today_movie_1, "The Battle"),
            TopMovieItem(R.drawable.today_movie_2, "Jurasic World"),
            TopMovieItem(R.drawable.today_movie_3, "The Batman")
        )

        val adapter = TopMovieAdapter(todayList) { item ->
            val intent = Intent(this, MovieDetailActivity::class.java)
            intent.putExtra(MovieDetailActivity.Companion.EXTRA_MOVIE, item)
            startActivity(intent)
        }
        binding.rvTodayPick.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayPick.adapter = adapter
    }
}