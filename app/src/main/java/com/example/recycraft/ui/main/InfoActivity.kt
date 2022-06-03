package com.example.recycraft.ui.main

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.androidplot.pie.Segment
import com.androidplot.pie.SegmentFormatter
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivityInfoBinding
import com.example.recycraft.ui.list.ListCraftActivity

class InfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Result")

        // get data and visualize pie chart
        val kategori = intent.getStringExtra(EXTRA_KATEGORI)
        val akurasi = intent.getFloatExtra(EXTRA_AKURASI, 0F)

        val value1 = (akurasi*100).toInt()
        val value2 = 100 - value1

        var kategori2 = "organic"
        if (kategori == kategori2) kategori2 = "recycleable"

        val s1 = Segment(kategori, value1)
        val s2 = Segment(kategori2, value2)

        val sf1 = SegmentFormatter(Color.GREEN)
        val sf2 = SegmentFormatter(Color.GRAY)

        binding.pieChart.addSegment(s1,sf1)
        binding.pieChart.addSegment(s2,sf2)

        // button cari kerajinan
        binding.btnCari.setOnClickListener {
            val moveIntent = Intent(this@InfoActivity, ListCraftActivity::class.java)
            startActivity(moveIntent)
        }
    }

    companion object {
        const val EXTRA_KATEGORI = "extra_kategori"
        const val EXTRA_AKURASI = "extra_akurasi"
    }
}