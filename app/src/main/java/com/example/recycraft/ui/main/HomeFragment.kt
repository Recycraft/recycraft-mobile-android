package com.example.recycraft.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycraft.R
import com.example.recycraft.adapter.DummyHorizontalAdapter
import com.example.recycraft.adapter.DummyVerticalAdapter
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.databinding.FragmentHomeBinding
import com.example.recycraft.ui.category.CategoryActivity
import com.example.recycraft.ui.search.SearchResultActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var rvCraft: RecyclerView
    private lateinit var rvCategory: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
/*
        binding?.apply {
            rvTopCrafts.setHasFixedSize(true)
            rvTopCrafts.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            rvTopCrafts.adapter = dummyHorizontalAdapter

            rvCategories.setHasFixedSize(true)
            rvCategories.layoutManager = LinearLayoutManager(activity)
            rvCategories.adapter = dummyVerticalAdapter

        }*/


//        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
//        (activity as AppCompatActivity)

        //vertical rv
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        rvCraft = view.findViewById(R.id.rvTopCrafts)

        val adapterCraft = DummyVerticalAdapter(ArrayCraft, activity)
        rvCraft.setHasFixedSize(true)
        rvCraft.layoutManager = lm
        rvCraft.adapter = adapterCraft

        //horizontal rv
        val vm = LinearLayoutManager(activity)
        vm.orientation = LinearLayoutManager.HORIZONTAL
        rvCategory = view.findViewById(R.id.rvCategories)

        val adapterCategory = DummyHorizontalAdapter(ArrayCategory, activity)
        rvCategory.setHasFixedSize(true)
        rvCategory.layoutManager = vm
        rvCategory.adapter = adapterCategory

        adapterCraft.setOnItemClickCallback(object : DummyVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(data: TopCraftsModel) {
                val intent = Intent(requireActivity(), CategoryActivity::class.java)
                //passing data
//                intent.putExtra()
                startActivity(intent)
            }
        })

        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(requireContext(), SearchResultActivity::class.java)
                intent.putExtra(SearchResultActivity.EXTRA_DATA, query)
                startActivity(intent)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private val ArrayCraft: ArrayList<TopCraftsModel>
        get() {
            val dataTitle = resources.getStringArray(R.array.titlesCraft)
            val dataCategoryCraft = resources.getStringArray(R.array.categoryCraft)
            val arrayCraft = ArrayList<TopCraftsModel>()
            for (i in dataTitle.indices) {
                val craft = TopCraftsModel(
                    R.drawable.kerajinanlampion,
                    dataTitle[i],
                    dataCategoryCraft[i]
                )
                arrayCraft.add(craft)
            }
            return arrayCraft
        }

    private val ArrayCategory: ArrayList<CategoriesModel>
        get() {
            val dataTitleCategory = resources.getStringArray(R.array.titlesCategories)
            val arrayCategory = ArrayList<CategoriesModel>()
            for (i in dataTitleCategory.indices) {
                val category = CategoriesModel(
                    R.drawable.sampahoverlay,
                    dataTitleCategory[i],
                )
                arrayCategory.add(category)
            }
            return arrayCategory
        }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }*/
}


