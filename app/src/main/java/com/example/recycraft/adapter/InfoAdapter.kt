package com.example.recycraft.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recycraft.databinding.ItemListSampahBinding
import com.example.recycraft.ui.camera.ScrapClassClassifier
import kotlin.math.roundToInt

class InfoAdapter(
    private val listScrap: ArrayList<ScrapClassClassifier.Classification>,
    var context: Activity?
) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {

    class ViewHolder(var binding: ItemListSampahBinding) : RecyclerView.ViewHolder(binding.root)

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemListSampahBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listScrap[position]) {
                val persentase = (akurasi * 10000.0).roundToInt() / 100.0
                val hasilPersentase = StringBuilder()
                binding.namaSampah.text = kategori
                binding.hasilPersentase.text = hasilPersentase.append(persentase).append("%")

                holder.itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(listScrap[holder.adapterPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int {
//        return listScrap.size
        return 3
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ScrapClassClassifier.Classification)
    }
}