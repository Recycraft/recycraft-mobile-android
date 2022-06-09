package com.example.recycraft.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.recycraft.R
import com.example.recycraft.data.model.UserRegisterResponse
import com.example.recycraft.data.remote.ApiConfig
import com.example.recycraft.databinding.ActivitySignupBinding
import com.example.recycraft.ui.login.LoginActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.login_button -> {
                val loginIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.signup_button -> {
                val registerIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }

    private fun requestCreateAccount() {
        val name = binding.nameInput.text.toString().trim()
        val email = binding.emailInput.text.toString().trim()
        val password = binding.passInput.text.toString().trim()
        showLoading(true)
        val client = ApiConfig.getApiService().createAccount(name, email, password)
        client.enqueue(object : Callback<UserRegisterResponse> {
            override fun onResponse(
                call: Call<UserRegisterResponse>,
                response: Response<UserRegisterResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this@SignupActivity, "Register Success", Toast.LENGTH_SHORT)
                        .show()
                    val mainIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                    showLoading(false)
                    startActivity(mainIntent)
                    finish()
                } else {
                    showLoading(false)
                    try {
                        val jObjError = JSONObject(response.errorBody()?.string())
                        Toast.makeText(
                            this@SignupActivity,
                            jObjError.getString("message"),
                            Toast.LENGTH_LONG
                        ).show()
                    } catch (e: Exception) {
                        Toast.makeText(this@SignupActivity, e.message, Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onFailure(call: Call<UserRegisterResponse>, t: Throwable) {

                Log.e("error : ", "${t.message}")
                Toast.makeText(this@SignupActivity, t.message.toString(), Toast.LENGTH_SHORT)
                    .show()

            }
        })

    }


    private fun validateCreateAccount(): Boolean {
        return if (binding.emailInput.text!!.isNotEmpty() && binding.passInput.text!!.isNotEmpty() && binding.nameInput.text.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.nameInput.text.toString())
                .matches()
            && binding.passInput.text.toString().length > 5
        ) {
            true
        } else {
            Toast.makeText(this, "Invalid Format", Toast.LENGTH_SHORT).show()
            false
        }

    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}