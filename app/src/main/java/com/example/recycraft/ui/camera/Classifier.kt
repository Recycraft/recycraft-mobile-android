package com.example.recycraft.ui.camera

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class Classifier (assetManager: AssetManager, modelPath: String, labelPath: String, inputSize: Int){
    private var interpreter: Interpreter
    private var labelList: List<String>
    private val inputSize: Int = inputSize
    private val pixelSize: Int = 3
    private val imageMean = 0
    private val imageStd = 255.0f
    private val maxResults = 3
    private val threshold = 0.4f

    data class Classification(
        var kategori: String = "",
        var akurasi: Float = 0F

    ) {
        override fun toString(): String {
            return "Kategori : $kategori, akurasi = $akurasi "
        }
    }

    init {
        val options = Interpreter.Options()
        options.setNumThreads(5)
//        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModel(assetManager, modelPath), options)
        labelList = loadLabel(assetManager, labelPath)
    }

    private fun loadModel(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun loadLabel(assetManager: AssetManager, labelPath: String): List<String> {
        return assetManager.open(labelPath).bufferedReader().useLines { it.toList() }
    }

    fun classifyImage(bitmap: Bitmap): List<Classification> {
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, false)
        val byteBuffer = convertBitmapToByteBuffer(scaledBitmap)
        val result = Array(1) { FloatArray(labelList.size) }
        interpreter.run(byteBuffer, result)
        return getSortedResult(result)
    }


    private fun convertBitmapToByteBuffer(bitmap: Bitmap): ByteBuffer {
        val byteBuffer = ByteBuffer.allocateDirect(4 * inputSize * inputSize * pixelSize)
        byteBuffer.order(ByteOrder.nativeOrder())
        val intValues = IntArray(inputSize * inputSize)

        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        var pixel = 0
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val input = intValues[pixel++]
                byteBuffer.putFloat((((input.shr(16) and 0xFF) - imageMean) / imageStd))
                byteBuffer.putFloat((((input.shr(8) and 0xFF) - imageMean) / imageStd))
                byteBuffer.putFloat((((input and 0xFF) - imageMean) / imageStd))
            }
        }
        return byteBuffer
    }


    private fun getSortedResult(labelProbArray: Array<FloatArray>): List<Classifier.Classification> {
        Log.d("Classifier", "List Size:(%d, %d, %d)".format(labelProbArray.size,labelProbArray[0].size,labelList.size))

        val pq = PriorityQueue(
            maxResults,
            Comparator<Classification> {
                    (_, akurasi1), (_, akurasi2)
                -> java.lang.Float.compare(akurasi1, akurasi2) * -1
            })

        for (i in labelList.indices) {
            val akurasi = labelProbArray[0][i]
            if (akurasi >= threshold) {
                pq.add(Classifier.Classification(
                    if (labelList.size > i) labelList[i] else "Unknown",
                    akurasi)
                )
            }
        }
        Log.d("Classifier", "pqsize:(%d)".format(pq.size))

        val recognitions = ArrayList<Classifier.Classification>()
        val recognitionsSize = Math.min(pq.size, maxResults)
        for (i in 0 until recognitionsSize) {
            recognitions.add(pq.poll())
        }
        return recognitions
    }
}