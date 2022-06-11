package com.example.recycraft.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recycraft.R
import com.example.recycraft.data.model.CraftsModel
import com.example.recycraft.databinding.ActivityDetailBinding
import com.example.recycraft.ui.main.MainActivity
import kotlinx.android.synthetic.main.detail_toolbar.view.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get and set data
        val dataCraft = intent.getParcelableExtra<CraftsModel>(EXTRA_CRAFT) as CraftsModel
        binding.tvDetailName.text = dataCraft.titleCraft
        Glide.with(binding.craftImage)
            .load(dataCraft.imageCraft)
            .error(R.drawable.ic_place_holder)
            .into(binding.craftImage)

        //html to text
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.craftDesc.text = Html.fromHtml(dataCraft.descCraft, Html.FROM_HTML_MODE_COMPACT)
            binding.tvScrollBahan.text =
                Html.fromHtml(dataCraft.materials, Html.FROM_HTML_MODE_COMPACT)
            binding.tvScrollTutorial.text =
                Html.fromHtml(dataCraft.process, Html.FROM_HTML_MODE_LEGACY)
        } else {
            binding.craftDesc.text = Html.fromHtml(dataCraft.descCraft)
            binding.tvScrollBahan.text = Html.fromHtml(dataCraft.materials)
            binding.tvScrollTutorial.text = Html.fromHtml(dataCraft.process)
        }

        binding.appBarResult.btn_back.setOnClickListener {
            val backIntent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }
}