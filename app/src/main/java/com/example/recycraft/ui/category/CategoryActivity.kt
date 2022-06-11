package com.example.recycraft.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.R
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.databinding.ActivityCategoryBinding
import com.example.recycraft.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.category_toolbar.view.*

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: CraftVerticalAdapter
    private val listListCraft = ArrayList<TopCraftsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //back button
        binding.appBarResult.LogoBack.setOnClickListener {
            finish()
        }

//        listListCraft.addAll(ArrayListCraft)
        adapter = CraftVerticalAdapter(/*listListCraft, this*/)

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@CategoryActivity)
            rvListKerajinan.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TopCraftsModel) {
                val moveIntent = Intent(this@CategoryActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_CRAFT, data)
                startActivity(moveIntent)
            }
        })

    }

//    private val ArrayListCraft: ArrayList<TopCraftsModel>
//        get() {
//            val dataListTitle = resources.getStringArray(R.array.titlesListCraft)
//            val dataListDesc = resources.getStringArray(R.array.descsListCraft)
//            val arrayListCraft = ArrayList<TopCraftsModel>()
//            for (i in dataListTitle.indices) {
//                val listCraft = TopCraftsModel(
//                    R.drawable.kerajinanlampion,
//                    dataListTitle[i],
//                    dataListDesc[i]
//                )
//                arrayListCraft.add(listCraft)
//            }
//            return arrayListCraft
//        }
}