package com.example.recycraft.ui.detail

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
            .into(binding.craftImage)

        //html to text
        binding.tvScrollBahan.text = Html.fromHtml(dataCraft.materials)
        binding.tvScrollTutorial.text = Html.fromHtml(dataCraft.process)

        binding.appBarResult.LogoBack.setOnClickListener {
            val backIntent = Intent(this@DetailActivity, MainActivity::class.java)
            startActivity(backIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_CRAFT = "extra_craft"
    }
}