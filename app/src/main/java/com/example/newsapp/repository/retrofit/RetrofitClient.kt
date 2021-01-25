package com.example.newsapp.repository.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/v2/"

    fun getClient(): Retrofit = Retrofit.Builder().run {
        baseUrl(BASE_URL)
        addConverterFactory(GsonConverterFactory.create())
        build()
    }
}