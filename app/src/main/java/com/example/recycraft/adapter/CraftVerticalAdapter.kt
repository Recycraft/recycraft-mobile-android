package com.example.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.databinding.VerticalRowBinding

class CraftVerticalAdapter(
    private val listCrafts: ArrayList<TopCraftsModel>,
    var context: Activity?
) : RecyclerView.Adapter<CraftVerticalAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: VerticalRowBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            VerticalRowBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listCrafts[position]) {
                binding.tvKerajinanName.text = titleCraft
                binding.tvKerajinanKategori.text = descCraft
                Glide.with(viewHolder.itemView.context)
                    .load(imageCraft)
                    .into(viewHolder.binding.imgKerajinanPhoto)

                viewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCrafts[viewHolder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listCrafts.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopCraftsModel)
    }
}