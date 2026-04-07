package com.example.movie_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.movie_app.Adapter.IntroAdapter
import com.example.movie_app.Model.MovieIntro
import com.example.movie_app.UI.HomeActivity
import com.example.movie_app.databinding.ActivityMainBinding
import kotlin.time.Instant

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecyclerView()
        goHompage()

        binding.textLogo.alpha = 0f
        binding.imgLogo.alpha = 0f
        binding.groupButton.alpha = 0f


        binding.textLogo.animate().alpha(1f).setStartDelay(3000).setDuration(500).start()
        binding.imgLogo.animate().alpha(1f).setStartDelay(3000).setDuration(500).start()
        binding.groupButton.animate().alpha(1f).setStartDelay(3000).setDuration(500).start()
    }

    private fun goHompage() {
        binding.buttonGo.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        val listIntro = mutableListOf<MovieIntro>()
        var midIndex = 1
        var sideLeftIndex = 1
        var sideRightIndex = 5

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