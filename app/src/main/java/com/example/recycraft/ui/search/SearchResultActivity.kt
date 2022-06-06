package com.example.recycraft.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivitySearchResultBinding

class SearchResultActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchResultBinding


    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}