package com.oye.recycraft.ui.list

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.R
import com.example.recycraft.databinding.ActivityListCraftBinding
import com.example.recycraft.model.ListCraftModel
import com.oye.recycraft.adapter.DummyListCraftAdapter
import com.oye.recycraft.ui.detail.DetailActivity

class ListCraftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCraftBinding
    private lateinit var adapter: DummyListCraftAdapter
    private val listListCraft = ArrayList<ListCraftModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listListCraft.addAll(ArrayListCraft)
        adapter = DummyListCraftAdapter(listListCraft, this)

        binding?.apply {
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