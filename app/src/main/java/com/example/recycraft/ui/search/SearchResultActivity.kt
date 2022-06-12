package com.example.recycraft.ui.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivitySearchResultBinding
import com.example.recycraft.ui.category.CategoryActivity
import com.example.recycraft.ui.detail.DetailActivity

class SearchResultActivity : AppCompatActivity() {

    lateinit var binding: ActivitySearchResultBinding
    private val listCraft = ArrayList<CraftsModel>()
    private lateinit var adapter: CraftVerticalAdapter

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = CraftVerticalAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@SearchResultActivity)
            rvListKerajinan.adapter = adapter
        }

        //get data
        val dataCraft =
            intent.getParcelableArrayListExtra<CraftsModel>(CategoryActivity.EXTRA_CRAFT)


        binding.etSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String): Boolean {
                listCraft.clear()
                showLoading(true)

                //filter data
                // berdasarkan judul
                val craft1 =
                    dataCraft?.filter { craft -> craft.titleCraft?.contains(p0, true) ?: false }
                //berdasarkan kategori
                val craft2 =
                    dataCraft?.filter { craft -> craft.categoryCraft.titleCategory?.lowercase() == p0.lowercase() }

                var craft = ArrayList<CraftsModel>()
                if (craft1 != null) {
                    craft.addAll(craft1)
                }
                if (craft2 != null) {
                    craft.addAll(craft2)
                }
                craft = craft.distinct() as ArrayList<CraftsModel>

                //set data
                adapter.setListCraft(craft)

                showLoading(false)
                binding.etSearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

        //onItemClick
        adapter.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(dataCraft: CraftsModel) {
                val intent = Intent(this@SearchResultActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CRAFT, dataCraft)
                startActivity(intent)
            }
        })

        //back button
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}