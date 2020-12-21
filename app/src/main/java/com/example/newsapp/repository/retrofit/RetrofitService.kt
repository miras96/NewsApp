package com.example.newsapp.repository.retrofit

import com.example.newsapp.models.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {
    @GET("top-headlines")
    suspend fun getLatestNews(@Query("country") country: String, @Query("apiKey") apiKey: String): ResponseModel
}