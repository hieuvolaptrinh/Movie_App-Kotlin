package com.example.movie_app.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app.R
import com.example.movie_app.data.model.movie.TopMovieItemResponse
import com.example.movie_app.databinding.ActivityHomeBinding
import com.example.movie_app.ui.home.adapter.TopMovieAdapter
import com.example.movie_app.ui.home.model.TopMovieItem
import com.example.movie_app.ui.movie_detail.MovieDetailActivity
import com.example.movie_app.until.Constants
import com.example.movie_app.until.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: TopMovieViewModel by viewModels()

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
        observePopularMovies()
        observeTodayMovies()

        viewModel.getPopularMovies()
        viewModel.getTodayMovies()

    }

    private fun observePopularMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.popularState.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Toast.makeText(
                                this@HomeActivity, "Loading popular movies...", Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Success -> initPopularMovie(
                            mapToUiMovies(
                                state.data, isPopular = true
                            )
                        )

                        is UiState.Error -> Toast.makeText(
                            this@HomeActivity, state.message, Toast.LENGTH_SHORT
                        ).show()

                        is UiState.Idle -> Unit
                    }
                }
            }
        }
    }

    private fun observeTodayMovies() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.todayState.collect { state ->
                    when (state) {
                        is UiState.Success -> initTodayMovie(
                            mapToUiMovies(
                                state.data,

                                isPopular = false,
                            )
                        )

                        is UiState.Error -> Toast.makeText(
                            this@HomeActivity, state.message, Toast.LENGTH_SHORT
                        ).show()

                        is UiState.Loading, is UiState.Idle -> Unit
                    }
                }
            }
        }
    }

    private fun initPopularMovie(popularList: List<TopMovieItem>) {
        binding.tvPopularTitle.text = getString(R.string.popular_movies)
        val adapter = TopMovieAdapter(popularList) { item -> openMovieDetail(item) }

        binding.rvPopular.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvPopular.adapter = adapter
    }

    private fun initTodayMovie(todayList: List<TopMovieItem>) {
        val adapter = TopMovieAdapter(todayList) { item -> openMovieDetail(item) }
        binding.rvTodayPick.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvTodayPick.adapter = adapter
    }

    private fun openMovieDetail(item: TopMovieItem) {
        val intent = Intent(this, MovieDetailActivity::class.java)
        intent.putExtra(Constants.EXTRA_MOVIE, item)
        startActivity(intent)
    }

    private fun mapToUiMovies(
        movies: List<TopMovieItemResponse>, isPopular: Boolean = false
    ): List<TopMovieItem> {

        val images = if (isPopular) {
            listOf(
                R.drawable.popular_1, R.drawable.popular_2, R.drawable.popular_3
            )
        } else {
            listOf(
                R.drawable.today_1, R.drawable.today_2, R.drawable.today_3
            )
        }

        return movies.mapIndexed { index, movie ->
            TopMovieItem(
                imageId = images.getOrElse(index) { images.last() }, name = movie.title
            )
        }
    }
}