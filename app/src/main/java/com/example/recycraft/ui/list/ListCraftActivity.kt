package com.example.recycraft.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.R
import com.example.recycraft.adapter.DummyListCraftAdapter
import com.example.recycraft.data.model.ListCraftModel
import com.example.recycraft.databinding.ActivityListCraftBinding
import com.example.recycraft.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.listcraft_toolbar.view.*

class ListCraftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCraftBinding
    private lateinit var adapter: DummyListCraftAdapter
    private val listListCraft = ArrayList<ListCraftModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        listListCraft.addAll(ArrayListCraft)
        adapter = DummyListCraftAdapter(listListCraft, this)

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@ListCraftActivity)
            rvListKerajinan.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : DummyListCraftAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListCraftModel) {
                val moveIntent = Intent(this@ListCraftActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_CRAFT, data)
                startActivity(moveIntent)
            }
        })

        binding.appBarResult.LogoBack.setOnClickListener {
            finish()
        }
    }

    private val ArrayListCraft: ArrayList<ListCraftModel>
        get() {
            val dataListTitle = resources.getStringArray(R.array.titlesListCraft)
            val dataListDesc = resources.getStringArray(R.array.descsListCraft)
            val arrayListCraft = ArrayList<ListCraftModel>()
            for (i in dataListTitle.indices) {
                val listCraft = ListCraftModel(
                    R.drawable.kerajinanlampion,
                    dataListTitle[i],
                    dataListDesc[i]
                )
                arrayListCraft.add(listCraft)
            }
            return arrayListCraft
        }
}