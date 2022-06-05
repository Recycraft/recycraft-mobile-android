package com.example.recycraft.ui.info

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.recycraft.databinding.ActivityInfoBinding
import com.example.recycraft.ui.list.ListCraftActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class InfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Result"

        // get data and visualize pie chart
        val type = intent.getStringExtra(EXTRA_TYPE)
        val confident = intent.getFloatExtra(EXTRA_CONFIDENT, 0F)

        val value1 = (confident*100)
        val value2 = 100 - value1

        val pieMap: MutableMap<String, Float>
        when (type?.lowercase()){
            "organic" -> pieMap = mutableMapOf("Organic" to value1, "Recycleable" to value2)
            else -> pieMap = mutableMapOf("Organic" to value2, "Recycleable" to value1)
        }

        val entries = ArrayList<PieEntry>()
        pieMap.filter { (key, value) ->
            entries.add(PieEntry(value, key))
        }

        val pieColors = ArrayList<Int>()
        pieColors.add(Color.parseColor("#54B086"))
        pieColors.add(Color.parseColor("#E9BC99"))

        val pieDataSet = PieDataSet(entries, "")
        pieDataSet.colors = pieColors
        pieDataSet.sliceSpace = 1f
        val pieData = PieData(pieDataSet)
        pieData.setValueTextSize(0f)

        binding.pieChart.apply {
            data = pieData
            animateY(1400, Easing.EaseInOutQuad)
            description.isEnabled = false
            legend.orientation = Legend.LegendOrientation.VERTICAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.CENTER
            setDrawEntryLabels(false)
            invalidate()
        }

        // button cari kerajinan
        binding.btnCari.setOnClickListener {
            val moveIntent = Intent(this@InfoActivity, ListCraftActivity::class.java)
            startActivity(moveIntent)
        }
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_CONFIDENT = "extra_confident"
        const val EXTRA_KATEGORI = "extra_kategori"
        const val EXTRA_AKURASI = "extra_akurasi"
        const val EXTRA_IMAGE = "image"
    }
}