package com.example.kindleota.retrofit

import com.example.kindleota.data.ResponseDataItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("json")
    fun getData(): Call<List<ResponseDataItem>>

    @GET("kindle/{kindle_no}")
    fun getKindle(@Path("kindle_no") numb: Int)
}