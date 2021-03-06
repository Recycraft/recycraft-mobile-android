package com.example.recycraft.ui.info

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
//import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recycraft.adapter.InfoAdapter
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityInfoBinding
import com.example.recycraft.ui.camera.ScrapClassClassifier
import com.example.recycraft.ui.list.ListCraftActivity
import com.example.recycraft.ui.main.HomeViewModel
import com.example.recycraft.ui.main.MainActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.result_toolbar.view.*
import kotlin.math.floor

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    private lateinit var adapter: InfoAdapter
    private var listScrap = ArrayList<ScrapClassClassifier.Classification>()

    private var nama: String? = null
    private var allCraft = ArrayList<CraftsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get image
        val img = intent.getStringExtra(EXTRA_IMAGE)
        Glide.with(this)
            .load(img)
            .into(binding.imageResult)

        // get data and visualize pie chart
        val type = intent.getStringExtra(EXTRA_TYPE)
//        Log.d("INFO_TYPE", "type: $type")
        val confident = intent.getFloatExtra(EXTRA_CONFIDENT, 0F)
//        Log.d("INFO_CONFIDENT", "confident: $confident")

        val value1 = floor(confident * 100)
        val value2 = 100 - value1

        val pieMap: MutableMap<String, Float> = when (type) {
            "organic" -> mutableMapOf("Organic" to value1, "Recyclable" to value2)
            else -> mutableMapOf("Organic" to value2, "Recyclable" to value1)
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
            legend.orientation = Legend.LegendOrientation.HORIZONTAL
            legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            legend.isWordWrapEnabled = true
            setDrawEntryLabels(false)
            invalidate()
        }

        //rv identifikasi sampah
        listScrap = intent.getParcelableArrayListExtra(EXTRA_IDENTIFY)!!
        adapter = InfoAdapter(listScrap, this)
        binding.apply {
            rvSampah.setHasFixedSize(true)
            rvSampah.layoutManager = LinearLayoutManager(this@InfoActivity)
            rvSampah.adapter = adapter
        }

        //adapter onclick
        adapter.setOnItemClickCallback(object : InfoAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ScrapClassClassifier.Classification) {
                nama = data.kategori
            }
        })

        //get data craft
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]
        viewModel.setAllCraft()
        viewModel.getAllCraft().observe(this) {
            if (it != null) {
                allCraft = it
            }
        }

        // button cari kerajinan
        binding.btnCari.setOnClickListener {
            if (nama != null) {
                val moveIntent = Intent(this@InfoActivity, ListCraftActivity::class.java)

                //kirim data craft - filtered
                val craft = allCraft.filter { craft -> craft.categoryCraft.slug == nama }
                val dataCraft = ArrayList<CraftsModel>()
                dataCraft.addAll(craft)
                moveIntent.putExtra(ListCraftActivity.EXTRA_CRAFT, dataCraft)

                startActivity(moveIntent)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Pilih hasil Classification terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.appBarResult.btn_back.setOnClickListener {
            val backIntent = Intent(this@InfoActivity, MainActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_TYPE = "extra_type"
        const val EXTRA_CONFIDENT = "extra_confident"
        const val EXTRA_IDENTIFY = "extra_identify"
        const val EXTRA_IMAGE = "image"
    }
}