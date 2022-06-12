package com.example.recycraft.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recycraft.adapter.CategoryHorizontalAdapter
import com.example.recycraft.adapter.CraftVerticalAdapter
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.FragmentHomeBinding
import com.example.recycraft.ui.category.CategoryActivity
import com.example.recycraft.ui.detail.DetailActivity
import com.example.recycraft.ui.list.ListCraftActivity
import com.example.recycraft.ui.search.SearchResultActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var allCraft = ArrayList<CraftsModel>()

    private lateinit var adapterCraft: CraftVerticalAdapter
    private lateinit var adapterCategory: CategoryHorizontalAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
//        (activity as AppCompatActivity)

        //craft adapter
        adapterCraft = CraftVerticalAdapter(/*ArrayCraft, activity*/)
        adapterCraft.notifyDataSetChanged()

        //category adapter
        adapterCategory = CategoryHorizontalAdapter()
        adapterCategory.notifyDataSetChanged()

        binding.apply {
            rvCrafts.setHasFixedSize(true)
            val craftLayout = LinearLayoutManager(activity)
            craftLayout.orientation = LinearLayoutManager.VERTICAL
            rvCrafts.layoutManager = craftLayout
            rvCrafts.adapter = adapterCraft

            rvCategories.setHasFixedSize(true)
            val categoryLayout = LinearLayoutManager(activity)
            categoryLayout.orientation = LinearLayoutManager.HORIZONTAL
            rvCategories.layoutManager = categoryLayout
            rvCategories.adapter = adapterCategory
        }

        //view model
        this.viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]

        //show loading
        showLoadingCraft(true)
        showLoadingCategory(true)

        //show rv
        this.viewModel.setAllCraft()
        this.viewModel.getAllCraft().observe(viewLifecycleOwner) {
            if (it != null) {
                adapterCraft.setListCraft(it)
                showLoadingCraft(false)
                allCraft = it
            }
        }
        this.viewModel.setAllCategory()
        this.viewModel.getAllCategory().observe(viewLifecycleOwner) {
            if (it != null) {
                adapterCategory.setListCategory(it)
                showLoadingCategory(false)
            }
        }

        //on item clicked
        adapterCraft.setOnItemClickCallback(object : CraftVerticalAdapter.OnItemClickCallback {
            override fun onItemClicked(dataCraft: CraftsModel) {
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_CRAFT, dataCraft)
                Log.d(" HOME DATA CRAFT", "dataCraft: $dataCraft")
                startActivity(intent)
            }
        })
        adapterCategory.setOnItemClickCallback(object :
            CategoryHorizontalAdapter.OnItemClickCallback {
            override fun onItemClicked(dataCategory: CategoriesModel) {
                val intent = Intent(requireActivity(), CategoryActivity::class.java)
                //kirim data sampah
                intent.putExtra(CategoryActivity.EXTRA_CATEGORY, dataCategory)
                Log.d(" HOME DATA CATEGORY", "dataCategory: $dataCategory")

                //kirim data craft - filtered
                val craft =
                    allCraft.filter { craft -> craft.categoryCraft.titleCategory == dataCategory.titleCategory }
                val dataCraft = ArrayList<CraftsModel>()
                dataCraft.addAll(craft)
                intent.putExtra(CategoryActivity.EXTRA_CRAFT, dataCraft)
                startActivity(intent)
            }
        })

        //button show all
        binding.btnShow.setOnClickListener {
            val intent = Intent(requireActivity(), ListCraftActivity::class.java)
            //kirim data craft - all
            intent.putExtra(ListCraftActivity.EXTRA_CRAFT, allCraft)
            startActivity(intent)
        }

        //search onclick
        binding.svSearch.setOnClickListener {
            val intent = Intent(requireContext(), SearchResultActivity::class.java)
            //kirim data all craft
            val dataCraft = allCraft
            intent.putExtra(SearchResultActivity.EXTRA_CRAFT, dataCraft)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoadingCraft(isLoading: Boolean) {
        binding.craftProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showLoadingCategory(isLoading: Boolean) {
        binding.categoryProgress.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}


