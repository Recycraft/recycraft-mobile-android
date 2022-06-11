package com.example.recycraft.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recycraft.data.model.CraftResponse
import com.example.recycraft.data.model.TopCraftsModel
import com.example.recycraft.data.model.CategoryResponse
import com.example.recycraft.data.model.ListCategory
import com.example.recycraft.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel(){
    val listCrafts = MutableLiveData<ArrayList<TopCraftsModel>>()
    val listCraftSlug = MutableLiveData<TopCraftsModel>()
    private val _listCategory = MutableLiveData<List<ListCategory>>()

    fun getListCategory(id : Int , name : String , slug : String, type : String, image : String , desc : String){
        val client = ApiConfig.getApiService().getAllCategory(id ,name , slug , type, image, desc)
        client.enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(
                call: Call<CategoryResponse>,
                response: Response<CategoryResponse>
            ) {
                if(response.isSuccessful){
                    _listCategory.postValue(response.body()?.listCategory)
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                Log.e("onFailure: " , t.message.toString())
            }
        })
    }

    fun getListCategory(): LiveData<List<ListCategory>> {
        return _listCategory
    }

    fun setAllCraft(){
        val client = ApiConfig.getApiService().getAllCraft()
        client.enqueue(object : Callback<CraftResponse> {
            override fun onResponse(call: Call<CraftResponse>, response: Response<CraftResponse>) {
                if (response.isSuccessful){
                    listCrafts.value = response.body()?.data
                } else {
                    Log.e("HomeViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CraftResponse>, t: Throwable) {
                Log.e("HomeViewModel", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getAllCraft(): LiveData<ArrayList<TopCraftsModel>> {
        return listCrafts
    }

    fun setCraftBySlug(slug: String){
        val client = ApiConfig.getApiService().getCraftBySlug(slug)
        client.enqueue(object : Callback<TopCraftsModel> {
            override fun onResponse(
                call: Call<TopCraftsModel>,
                response: Response<TopCraftsModel>
            ) {
                if (response.isSuccessful) {
                    listCraftSlug.value = response.body()
                } else {
                    Log.e("CraftBySlug", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopCraftsModel>, t: Throwable) {
                Log.e("CraftBySlug", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun getCraftBySlug() : LiveData<TopCraftsModel> {
        return listCraftSlug
    }
}