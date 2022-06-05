package com.example.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycraft.databinding.ItemListKerajinanBinding
import com.example.recycraft.data.model.ListCraftModel

class DummyListCraftAdapter(
    private val listListCraft: ArrayList<ListCraftModel>,
    var context: Activity?
) : RecyclerView.Adapter<DummyListCraftAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: ItemListKerajinanBinding) :
        RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: DummyListCraftAdapter.OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: DummyListCraftAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListKerajinanBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listListCraft[position]) {
                binding.tvResultList.text = titleListCraft
                binding.tvDescList.text = descListCraft
                Glide.with(viewHolder.itemView.context)
                    .load(imageListCraft)
                    .into(viewHolder.binding.imgResultList)

                viewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listListCraft[viewHolder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listListCraft.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ListCraftModel)
    }
}