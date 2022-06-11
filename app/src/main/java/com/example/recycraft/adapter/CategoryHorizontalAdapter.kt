package com.example.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycraft.data.model.CategoriesModel
import com.example.recycraft.data.model.ListCategory
import com.example.recycraft.databinding.HorizontalRowBinding
/*
class CategoryHorizontalAdapter(
    private val listCategory: ArrayList<CategoriesModel>,
    var context: Activity?
) : RecyclerView.Adapter<CategoryHorizontalAdapter.ViewHolder>() {

    inner class ViewHolder(var binding: HorizontalRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HorizontalRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return listCategory.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listCategory[position]) {
                binding.tvKategoriName.text = titleCategory
                Glide.with(viewHolder.itemView.context)
                    .load(imageCategory)
                    .into(viewHolder.binding.imgKategoriPhoto)

                viewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCategory[viewHolder.adapterPosition])
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoriesModel)
    }
}
*/

class CategoryHorizontalAdapter :RecyclerView.Adapter<CategoryHorizontalAdapter.ViewHolder>(){

    private val listCategory = ArrayList<ListCategory>()

    private var onItemClickCallback : OnItemClickCallback? =null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    inner class ViewHolder(val binding: HorizontalRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listCategory: ListCategory){
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(listCategory)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(listCategory.image)
                    .into(imgKategoriPhoto)
                tvKategoriName.text = listCategory.name
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            HorizontalRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listCategory[position])
    }

    override fun getItemCount(): Int = listCategory.size

    interface OnItemClickCallback{
        fun onItemClicked(data : ListCategory)

    }

}