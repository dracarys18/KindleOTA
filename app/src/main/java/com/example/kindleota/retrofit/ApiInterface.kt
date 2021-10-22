package com.example.kindleota

import com.example.kindleota.data.ResponseDataItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("json")
    fun getData(): Call<List<ResponseDataItem>>
}