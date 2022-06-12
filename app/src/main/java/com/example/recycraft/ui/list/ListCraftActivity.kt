package com.example.recycraft.ui.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityListCraftBinding
import com.example.recycraft.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.listcraft_toolbar.view.*

class ListCraftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCraftBinding
    private lateinit var adapter: CraftVerticalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CraftVerticalAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@ListCraftActivity)
            rvListKerajinan.adapter = adapter
        }

        showLoading(true)

        //get data craft
        val dataCraft = intent.getParcelableArrayListExtra<CraftsModel>(EXTRA_CRAFT)
        if (dataCraft != null) {
            adapter.setListCraft(dataCraft)
            showLoading(false)
        }

        adapter.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(dataCraft: CraftsModel) {
                val moveIntent = Intent(this@ListCraftActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_CRAFT, dataCraft)
                startActivity(moveIntent)
            }
        })

        binding.appBarResult.btn_back.setOnClickListener {
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }
}