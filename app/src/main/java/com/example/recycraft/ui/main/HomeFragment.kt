package com.example.recycraft.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycraft.R
import com.example.recycraft.databinding.FragmentHomeBinding
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.adapter.DummyHorizontalAdapter
import com.example.recycraft.adapter.DummyVerticalAdapter

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private lateinit var rvCraft: RecyclerView
    private lateinit var rvCategory: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
/*
        binding?.apply {
            rvTopCrafts.setHasFixedSize(true)
            rvTopCrafts.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            rvTopCrafts.adapter = dummyHorizontalAdapter

            rvCategories.setHasFixedSize(true)
            rvCategories.layoutManager = LinearLayoutManager(activity)
            rvCategories.adapter = dummyVerticalAdapter

        }*/
        //horizontal rv
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.HORIZONTAL
        rvCraft = view.findViewById(R.id.rvTopCrafts)

        val adapterCraft = DummyHorizontalAdapter(ArrayCraft, activity)
        rvCraft.setHasFixedSize(true)
        rvCraft.layoutManager = lm
        rvCraft.adapter = adapterCraft

        //vertical rv
        val vm = LinearLayoutManager(activity)
        vm.orientation = LinearLayoutManager.VERTICAL
        rvCategory = view.findViewById(R.id.rvCategories)

        val adapterCategory = DummyVerticalAdapter(ArrayCategory, activity)
        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager = vm
        rvCategory.adapter = adapterCategory

        return view
    }

    private val ArrayCraft: ArrayList<TopCraftsModel>
        get() {
            val dataTitle = resources.getStringArray(R.array.titlesCraft)

            val arrayCraft = ArrayList<TopCraftsModel>()
            for (i in dataTitle.indices) {
                val craft = TopCraftsModel(
                    R.drawable.kerajinanlampion,
                    dataTitle[i]
                )
                arrayCraft.add(craft)
            }
            return arrayCraft
        }

    private val ArrayCategory: ArrayList<CategoriesModel>
        get() {
            val dataTitleCategory = resources.getStringArray(R.array.titlesCategories)
            val dataDescCategiry = resources.getStringArray(R.array.descCategories)
            val arrayCategory = ArrayList<CategoriesModel>()
            for (i in dataTitleCategory.indices) {
                val category = CategoriesModel(
                    R.drawable.sampahoverlay,
                    dataTitleCategory[i],
                    dataDescCategiry[i]
                )
                arrayCategory.add(category)
            }
            return arrayCategory
        }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}


