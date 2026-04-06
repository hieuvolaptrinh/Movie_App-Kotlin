package com.example.movie_app

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movie_app.Adapter.IntroAdapter
import com.example.movie_app.Model.MovieIntro
import com.example.movie_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        val listIntro = mutableListOf<MovieIntro>()

        var midIndex = 1
        var sideLeftIndex = 1
        var sideRightIndex = 5

        // Tạo 5 hàng: SIDE_LEFT(35%) + MID(30%) + SIDE_RIGHT(35%)
        repeat(5) {
            val sideLeft = getDrawableId("side_intro_$sideLeftIndex")
            listIntro.add(MovieIntro(sideLeft, false))  // 35%
            sideLeftIndex++

            val mid = getDrawableId("mid_intro_$midIndex")
            listIntro.add(MovieIntro(mid, true))  // 30%
            midIndex++

            val sideRight = getDrawableId("side_intro_$sideRightIndex")
            listIntro.add(MovieIntro(sideRight, false))  // 35%
            sideRightIndex++
        }

        val adapter = IntroAdapter(listIntro)

        binding.recyclerViewIntro.layoutManager =
            StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL).apply {

                binding.recyclerViewIntro.adapter = adapter
            }

    }

    private fun getDrawableId(name: String): Int {
        return resources.getIdentifier(name, "drawable", packageName)
    }
}