package com.example.movie_app.ui.movie_detail

import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movie_app.ui.home.TopMovieItem
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
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        val movie = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra("movie", TopMovieItem::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<TopMovieItem>(EXTRA_MOVIE)
        }

        movie?.let {
            binding.imageMovie.setImageResource(it.imageId)
            binding.tvMovieName.text = it.name
        }


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
        initRcvCaster()
        changeReadMoreColor()

    }

    private fun changeReadMoreColor() {
        val content =
            "Black Widow is a 2021 American superhero film based on Marvel Comics featuring the character of the same name. Produced by Marvel Studios and distributed by Walt Disney Studios Motion Pictures, it is the 24th film in the Marvel Cinematic Universe (MCU).."
        val readMore = " Read More"
        val fullText = content + readMore

        val spannable = SpannableStringBuilder(fullText)

        val start = fullText.indexOf(readMore)
        val end = start + readMore.length

        spannable.setSpan(
            ForegroundColorSpan(Color.RED), // Màu đỏ
            start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        spannable.setSpan(
            StyleSpan(Typeface.BOLD), // In đậm
            start, end,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.tvDescription.text = spannable
    }

    fun initRcvCaster() {
        val casters = listOf(
            R.drawable.cast_1, R.drawable.cast_2, R.drawable.cast_3, R.drawable.cast_4,
        )
        val casterAdapter = CasterAdapter(casters)
        binding.rvCaster.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = casterAdapter
        }
    }

    companion object {
        const val EXTRA_MOVIE = "movie"
    }
}