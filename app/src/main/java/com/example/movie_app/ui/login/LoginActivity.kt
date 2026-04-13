package com.example.movie_app.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.movie_app.R
import com.example.movie_app.data.remote.RetrofitClient
import com.example.movie_app.data.repository.AuthRepository
import com.example.movie_app.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

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
        initViewModel()
        onclickLogin()
        observeState()
    }

    fun initViewModel() {
        val api = RetrofitClient.authApi
        val respository = AuthRepository(api)
        viewModel = ViewModelProvider(
            this, LoginViewModelFactory(respository)
        ).get(LoginViewModel::class.java)
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
        viewModel.state.observe(this) { state ->
            when (state) {
                is LoginState.Loading -> {
                    Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
                }

                is LoginState.Success -> {
                    Toast.makeText(
                        this, "Login successful! Token: ${state.data.toString()}", Toast.LENGTH_LONG
                    ).show()
                }

                is LoginState.Error -> {
                    Toast.makeText(this, "Login failed: ${state.mesage}", Toast.LENGTH_LONG).show()
                }

                else -> {
                    Toast.makeText(this, "Unknown state", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}