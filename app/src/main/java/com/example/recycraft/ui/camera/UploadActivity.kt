package com.example.recycraft.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recycraft.databinding.ActivityUploadBinding
import com.example.recycraft.ui.main.InfoActivity

class UploadActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadBinding
    private lateinit var  bitmap: Bitmap
    private var photo : String? = null
    private lateinit var typeClassifier: ScrapTypeClassifier
    private lateinit var categoryClassifier: ScrapClassClassifier

    companion object {
        const val REQ_CAMERA = 1
        const val REQ_GALLERY = 2
        const val mInputSize = 150
        const val STCModel = "stc_best_model.tflite"
        const val STCLabel = "stc.txt"
        const val SCDModel = "model_scd_xception.tflite"
        const val SCDLabel = "scd.txt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cameraButton.setOnClickListener {
            startActivityForResult(Intent(this,CameraActivity::class.java), REQ_CAMERA)
        }
        binding.galleryButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, REQ_GALLERY)
        }
        binding.uploadButton.setOnClickListener {
            typeClassifier = ScrapTypeClassifier(assets, STCModel, STCLabel, mInputSize)
            val typeResult = typeClassifier.classifyImage(bitmap)
            Log.d("STC", typeResult.toString())

            categoryClassifier = ScrapClassClassifier(assets, SCDModel, SCDLabel, mInputSize)
            val categoryResult = categoryClassifier.classifyImage(bitmap)
            Log.d("SCD", categoryResult.toString())

            val type = typeResult[0].type
            val confident = typeResult[0].confident

            val kategori = categoryResult[0].kategori
            val akurasi = categoryResult[0].akurasi

            binding.tvDetailName.text = type
            binding.tvKategoriName.text = "$confident"
            // move to InfoActivity
            val moveIntent = Intent(this@UploadActivity, InfoActivity::class.java)
            moveIntent.putExtra(InfoActivity.EXTRA_TYPE, type)
            moveIntent.putExtra(InfoActivity.EXTRA_CONFIDENT, confident)
            moveIntent.putExtra(InfoActivity.EXTRA_KATEGORI, kategori)
            moveIntent.putExtra(InfoActivity.EXTRA_AKURASI, akurasi)
            startActivity(moveIntent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQ_CAMERA){
            if(resultCode == Activity.RESULT_OK){
                val uri = data?.getStringExtra(CameraActivity.CEK_URI)
                Glide.with(this)
                    .load(uri)
                    .into(binding.previewImageView)
                photo = uri
                val u = Uri.parse(uri)
                bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, u)
            }
        }
        else if (requestCode == REQ_GALLERY && resultCode == Activity.RESULT_OK){
            val uri: Uri? =  data?.data
            photo = uri.toString()
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            Glide.with(this).load(uri).into(binding.previewImageView)
        }
    }
}
