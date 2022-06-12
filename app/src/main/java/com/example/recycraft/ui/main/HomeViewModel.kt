package com.example.recycraft.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recycraft.data.model.*
import com.example.recycraft.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    val listCrafts = MutableLiveData<ArrayList<CraftsModel>>()
    val listCategory = MutableLiveData<ArrayList<CategoriesModel>>()

    fun setAllCategory() {
        val client = ApiConfig.getApiService().getAllCategory()
        client.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if (response.isSuccessful) {
                    listCategory.value = response.body()?.dataCategory
                } else {
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllCategory(): LiveData<ArrayList<CategoriesModel>> {
        return listCategory
    }

    fun setAllCraft() {
        val client = ApiConfig.getApiService().getAllCraft()
        client.enqueue(object : Callback<CraftResponse> {
            override fun onResponse(call: Call<CraftResponse>, response: Response<CraftResponse>) {
                if (response.isSuccessful) {
                    listCrafts.value = response.body()?.dataCraft
                } else {
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CraftResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getAllCraft(): LiveData<ArrayList<CraftsModel>> {
        return listCrafts
    }

}