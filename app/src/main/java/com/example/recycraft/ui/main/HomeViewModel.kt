package com.example.recycraft.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recycraft.data.model.CategoryResponse
import com.example.recycraft.data.model.ListCategory
import com.example.recycraft.data.remote.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

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

}


