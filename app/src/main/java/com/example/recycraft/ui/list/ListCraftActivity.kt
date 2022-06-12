package com.example.recycraft.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityListCraftBinding
import com.example.recycraft.ui.detail.DetailActivity
import com.example.recycraft.ui.main.HomeViewModel
import kotlinx.android.synthetic.main.listcraft_toolbar.view.*

class ListCraftActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListCraftBinding
    private lateinit var adapter: CraftVerticalAdapter
    private val listListCraft = ArrayList<CraftsModel>()
    private lateinit var viewModel: HomeViewModel

    private var allCraft = ArrayList<CraftsModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListCraftBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        listListCraft.addAll(ArrayListCraft)
        adapter = CraftVerticalAdapter(/*listListCraft, this*/)

        binding.apply {
            rvListKerajinan.setHasFixedSize(true)
            rvListKerajinan.layoutManager = LinearLayoutManager(this@ListCraftActivity)
            rvListKerajinan.adapter = adapter
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