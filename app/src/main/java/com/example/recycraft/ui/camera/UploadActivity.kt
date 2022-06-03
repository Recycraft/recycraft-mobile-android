package com.example.recycraft.ui.camera

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore

import com.bumptech.glide.Glide
import com.example.recycraft.databinding.ActivityUploadBinding

import com.example.recycraft.ml.ScdBestModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer

import java.nio.ByteBuffer
import java.nio.ByteOrder

class UploadActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUploadBinding

    private lateinit var  bitmap: Bitmap

    private var photo : String? = null

    private lateinit var classifier: Classifier

    companion object {
        const val REQ_CAMERA = 1
        const val REQ_GALLERY = 2
        const val mInputSize = 150
        const val mModelPath = "scd_best_model.tflite"
        const val mLabelPath = "scd.txt"
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
            classifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)
            val result = classifier.classifyImage(bitmap)
            binding.tvDetailName.setText(result.get(0).kategori)
            binding.tvKategoriName.setText(""+result.get(0).akurasi)

        }
           /*
            val listScd = getFileName("scd.txt")
            val model = ScdBestModel.newInstance(this)

            val inputFeature0 = TensorBuffer.createFixedSize(
                intArrayOf(1,150,150,3),DataType.FLOAT32
            )
            inputFeature0.loadBuffer(getImageData(150))

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val max = getMax(outputFeature0.floatArray,5)
            val name = listScd[max]

            model.close()
            binding.tvDetailName.setText(name)
        }
*/

        }

    /*
    private fun getImageData(num: Int) :ByteBuffer {
        val resized : Bitmap = Bitmap.createScaledBitmap(bitmap, num, num, true)
        val imgData: ByteBuffer = ByteBuffer.allocateDirect(Float.SIZE_BYTES * num * num * 3)
        imgData.order(ByteOrder.nativeOrder())

        val intValues = IntArray(num * num)
        resized.getPixels(intValues, 0, resized.width, 0, 0, resized.width, resized.height)

        var pixel = 0
        for (i in 0 until num) {
            for (j in 0 until num) {
                val `val` = intValues[pixel++]
                imgData.putFloat((`val` shr 16 and 0xFF) / 255f)
                imgData.putFloat((`val` shr 8 and 0xFF) / 255f)
                imgData.putFloat((`val` and 0xFF) / 255f)
            }
        }
        return imgData
    }*/

    /*
    private fun getFileName(name: String): List<String>{
        val inputString = application.assets.open(name).bufferedReader().use { it.readText() }
        return inputString.split("\n")
    }
     */



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

            Glide.with(this).load(data?.data).into(binding.previewImageView)
        }
    }

    /*
    fun getMax(arr: FloatArray, j: Int): Int{
        var ind = 0
        var min = 0.0F

        for (i in 0..j)
        {
            if(arr[i] > min){
                ind = i
                min = arr[i]
            }
        }
        return ind

    }

     */

}
