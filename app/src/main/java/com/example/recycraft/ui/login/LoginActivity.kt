package com.example.recycraft.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivityLoginBinding
import com.example.recycraft.ui.main.MainActivity
import com.example.recycraft.ui.signup.SignupActivity

class LoginActivity : AppCompatActivity(),View.OnClickListener {
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener(this)
        binding.signupButton.setOnClickListener(this)


    }

    override fun onClick(view: View) {
        when (view.id){
            R.id.login_button ->{
                val loginIntent = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(loginIntent)
                finish()
            }
            R.id.signup_button ->{
                val registerIntent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(registerIntent)
            }
        }
    }
}