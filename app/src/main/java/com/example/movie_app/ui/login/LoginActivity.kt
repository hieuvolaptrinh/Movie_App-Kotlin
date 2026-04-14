package com.example.movie_app.ui.login

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
import com.example.movie_app.R
import com.example.movie_app.databinding.ActivityLoginBinding
import com.example.movie_app.until.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        onclickLogin()
        observeState()
    }


    fun onclickLogin() {
        binding.button.setOnClickListener {
            val username = binding.edtUsername.text.toString().trim()
            val password = binding.edtPassword.text.toString()
            viewModel.login(username, password)
        }
    }

    // Observe state
    fun observeState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { state ->
                    when (state) {
                        is UiState.Loading -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Logging in...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is UiState.Success -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login successful! Token: ${state.data}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is UiState.Error -> {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed: ${state.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is UiState.Idle -> {
                            // Không làm gì
                        }
                    }
                }
            }
        }

    }
}