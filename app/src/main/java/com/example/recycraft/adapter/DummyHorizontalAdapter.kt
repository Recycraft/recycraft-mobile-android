package com.example.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.databinding.HorizontalRowBinding

class DummyHorizontalAdapter(
    private val listCraft: ArrayList<TopCraftsModel>,
    var context: Activity?
) : RecyclerView.Adapter<DummyHorizontalAdapter.ViewHolder>() {

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
        return listCraft.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        with(viewHolder) {
            with(listCraft[position]) {
                binding.tvItemName.text = titleCraft
                Glide.with(viewHolder.itemView.context)
                    .load(imageCraft)
                    .into(viewHolder.binding.imgItemPhoto)

                viewHolder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listCraft[viewHolder.adapterPosition])
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: TopCraftsModel)
    }
}