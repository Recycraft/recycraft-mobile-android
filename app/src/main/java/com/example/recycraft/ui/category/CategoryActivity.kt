package com.example.recycraft.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityCategoryBinding
import com.example.recycraft.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.category_toolbar.view.*

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    //  private lateinit var adapter: CraftHorizontalAdapter
    //private val listListCraft = ArrayList<CraftsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //back button
        binding.appBarResult.LogoBack.setOnClickListener {
            finish()
        }

        //get and set
        val dataCategory = intent.getParcelableExtra<CategoriesModel>(EXTRA_CATEGORY)
        binding.tvTitleCategory.text = Html.fromHtml(dataCategory!!.titleCategory)
        binding.tvDescCategory.text = Html.fromHtml(dataCategory.descCategory)


/*
//        listListCraft.addAll(ArrayListCraft)
        adapter = CraftVerticalAdapter(/*listListCraft, this*/)

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@CategoryActivity)
            rvListKerajinan.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: CraftsModel) {
                val moveIntent = Intent(this@CategoryActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_CRAFT, data)
                startActivity(moveIntent)
            }
        })

    }*/

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

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
    }
}
