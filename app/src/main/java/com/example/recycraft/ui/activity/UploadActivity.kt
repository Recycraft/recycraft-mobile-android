package com.oye.recycraft.ui.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.recycraft.databinding.ActivityUploadBinding
import com.example.recycraft.ml.ScdBestModel
import com.example.recycraft.ml.StcBestModel
import createTempFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import uriToFile
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class UploadActivity : AppCompatActivity() {

    lateinit var gallery_button: Button
    lateinit var upload_button: Button
    lateinit var preview_image: ImageView
    lateinit var tv_name: TextView
    lateinit var tv_kategori: TextView
    lateinit var camera_button: Button
    lateinit var bitmap: Bitmap
    private lateinit var binding: ActivityUploadBinding

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gallery_button = binding.galleryButton
        camera_button = binding.cameraButton
        upload_button = binding.uploadButton
        tv_name = binding.tvDetailName
        tv_kategori = binding.tvKategoriName
        preview_image = binding.previewImageView

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                100
            )
        }

        val scd =
            application.assets.open("scd.txt").bufferedReader().use { it.readText() }.split("\n")
        val stc =
            application.assets.open("stc.txt").bufferedReader().use { it.readText() }.split("\n")

        camera_button.setOnClickListener(View.OnClickListener {
            var camera: Intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, 200)
        })
        gallery_button.setOnClickListener(View.OnClickListener {
            var intent: Intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*image/*"
            startActivityForResult(intent, 250)
        })

        upload_button.setOnClickListener(View.OnClickListener {

            var resized = Bitmap.createScaledBitmap(bitmap, 150, 150, true)

            val modelScd = ScdBestModel.newInstance(this)
            val modelStc = StcBestModel.newInstance(this)

            var tbuffer = TensorImage.fromBitmap(resized)
            var byteBuffer = tbuffer.buffer


            //create input
            val inputfFeature0 = TensorBuffer.createFixedSize(
                intArrayOf(1, 150, 150, 3),
                DataType.FLOAT32
//                DataType.UINT8
            )
            Log.d("shape", byteBuffer.toString())
            Log.d("shape", inputfFeature0.buffer.toString())
            inputfFeature0.loadBuffer(byteBuffer)

            val outputsScd = modelScd.process(inputfFeature0)
            val outputsStc = modelStc.process(inputfFeature0)
            val outputFeature0 = outputsScd.outputFeature0AsTensorBuffer
            val outputFeature1 = outputsStc.outputFeature0AsTensorBuffer


            var max0 = getMax(outputFeature0.floatArray)
            var max1 = getMax(outputFeature1.floatArray)

            tv_name.setText(scd[max0])
            tv_kategori.setText(stc[max1])

            modelScd.close()
            modelStc.close()

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 250) {

            preview_image.setImageURI(data?.data)
            var uri: Uri? = data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)


        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            bitmap = data?.extras?.get("data") as Bitmap
            preview_image.setImageBitmap(bitmap)

        }
    }

    fun getMax(arr: FloatArray): Int {
        var ind = 0;
        var min = 0.0F;

        for (i in 0..1000) {
            if (arr[i] > min) {
                min = arr[i]
                ind = i;
            }
        }
        return ind

    }

    fun checkAndGetPermissions() {
        if(checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
        }
    }
}
