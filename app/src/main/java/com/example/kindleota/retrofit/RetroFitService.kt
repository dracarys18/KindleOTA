package com.example.kindleota.retrofit

import com.example.kindleota.ApiInterface
import com.example.kindleota.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroService {
    fun getRetrofit(baseurl: CharSequence): Retrofit {
        return retrofit2.Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build()
    }

    fun createRetroInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}