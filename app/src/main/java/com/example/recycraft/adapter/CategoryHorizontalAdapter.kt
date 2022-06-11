package com.example.recycraft.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.databinding.HorizontalRowBinding

class CategoryHorizontalAdapter : RecyclerView.Adapter<CategoryHorizontalAdapter.ViewHolder>() {

    private val listCategory = ArrayList<CategoriesModel>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: CategoryHorizontalAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ViewHolder(var binding: HorizontalRowBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HorizontalRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listCategory[position]) {
                binding.tvKategoriName.text = titleCategory
                com.bumptech.glide.Glide.with(itemView.context)
                    .load(imageCategory)
                    .into(binding.imgKategoriPhoto)

                itemView.setOnClickListener {
                    onItemClickCallback?.onItemClicked(listCategory[viewHolder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = listCategory.size

    interface OnItemClickCallback {
        fun onItemClicked(dataCategory: CategoriesModel)

    }

    fun setListCategory(items: ArrayList<CategoriesModel>) {
        listCategory.clear()
        listCategory.addAll(items)
        notifyDataSetChanged()
    }

}