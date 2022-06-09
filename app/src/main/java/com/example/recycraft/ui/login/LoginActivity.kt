package com.example.recycraft.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recycraft.R
import com.example.recycraft.data.model.UserLoginResponse
import com.example.recycraft.data.remote.ApiConfig
import com.example.recycraft.databinding.ActivityLoginBinding
import com.example.recycraft.ui.main.MainActivity
import com.example.recycraft.ui.signup.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isSaveLoginInfo = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isSaveLoginInfo = sharedPreferences.getBoolean(CHECKBOX, false)
        saveloginInfo(isSaveLoginInfo)

        binding.loginButton.setOnClickListener(this)
        binding.signupButton.setOnClickListener(this)


    }

    private fun saveloginInfo(boolean: Boolean) {
        if (boolean) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun login() {
        showLoading(true)
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passInput.text.toString().trim()
        val client = ApiConfig.getApiService().getLoginUser(email, password)
        client.enqueue(object : Callback<UserLoginResponse> {
            override fun onResponse(
                call: Call<UserLoginResponse>,
                response: Response<UserLoginResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.userResult?.apply {
                        validateLogin(userId, name, token)
                    }
                    Intent(this@LoginActivity, MainActivity::class.java).also { startActivity(it) }
                    showLoading(false)
                    finish()
                } else {
                    showLoading(false)
                    Toast.makeText(
                        this@LoginActivity,
                        "Data yang dimasukan tidak valid",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<UserLoginResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(
                    this@LoginActivity,
                    "Data yang dimasukan tidak valid",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_button -> {
                val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.signup_button -> {
                val registerIntent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }

    private fun validateLogin(userId: Int, name: String, token: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putInt(USER_ID, userId)
        editor.putString(TOKEN, token)
        editor.putBoolean(CHECKBOX, binding.cbRemember.isChecked)
        editor.apply()
    }

    companion object {
        const val SHARED_PREFERENCES = "shared_preferences"
        const val CHECKBOX = "checkbox"
        const val NAME = "name"
        const val USER_ID = "user_id"
        const val TOKEN = "token"
    }
}