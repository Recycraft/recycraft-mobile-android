package com.example.recycraft.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recycraft.databinding.ActivityDetailBinding
import com.example.recycraft.ui.main.MainActivity
import kotlinx.android.synthetic.main.detail_toolbar.view.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appBarResult.LogoBack.setOnClickListener {
            val backIntent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }
}