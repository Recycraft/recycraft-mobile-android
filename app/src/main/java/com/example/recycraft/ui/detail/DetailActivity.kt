package com.oye.recycraft.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.recycraft.R

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }
}