package com.example.recycraft.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivitySignupBinding
import com.example.recycraft.ui.login.LoginActivity

class SignupActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener(this)
        binding.loginButton.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.login_button ->{
                val loginIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.signup_button ->{
                val registerIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }
}