package com.oye.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycraft.databinding.VerticalRowBinding
import com.example.recycraft.model.CategoriesModel

class DummyVerticalAdapter(
    private val listCategory: ArrayList<CategoriesModel>,
    var context: Activity?
) : RecyclerView.Adapter<DummyVerticalAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: VerticalRowBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: DummyVerticalAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: DummyVerticalAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VerticalRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listCategory[position]) {
                binding.tvKategoriName.text = titleCategory
                binding.tvDeskripsi.text = descCategory
                Glide.with(viewHolder.itemView.context)
                    .load(imageCategory)
                    .into(viewHolder.binding.imgKategoriPhoto)

                viewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCategory[viewHolder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCategory.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: CategoriesModel)
    }
}