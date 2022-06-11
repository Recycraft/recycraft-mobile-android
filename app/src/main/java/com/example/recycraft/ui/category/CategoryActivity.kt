package com.example.recycraft.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityCategoryBinding
import com.example.recycraft.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.category_toolbar.view.*

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: CraftVerticalAdapter
//    private val listListCraft = ArrayList<CraftsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get and set data category
        val dataCategory = intent.getParcelableExtra<CategoriesModel>(EXTRA_CATEGORY)
        binding.apply {
            tvTitleCategory.text = dataCategory?.titleCategory
            tvDescCategory.text = Html.fromHtml(dataCategory?.descCategory)
        }

        //back button
        binding.appBarResult.btn_back.setOnClickListener {
            finish()
        }

        adapter = CraftVerticalAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@CategoryActivity)
            rvListKerajinan.adapter = adapter
        }

        //get and set data craft
        val dataCraft = intent.getParcelableArrayListExtra<CraftsModel>(EXTRA_CRAFT)
        if (dataCraft != null) {
            adapter.setListCraft(dataCraft)
        }

        adapter.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(dataCraft: CraftsModel) {
                val moveIntent = Intent(this@CategoryActivity, DetailActivity::class.java)
                moveIntent.putExtra(DetailActivity.EXTRA_CRAFT, dataCraft)
                startActivity(moveIntent)
            }
        })

    }

    companion object {
        const val EXTRA_CATEGORY = "extra_category"
        const val EXTRA_CRAFT = "extra_craft"
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