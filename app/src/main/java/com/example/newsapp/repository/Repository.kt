package com.example.newsapp.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.models.ResponseModel
import com.example.newsapp.repository.retrofit.RetrofitService
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitService: RetrofitService
) {
    suspend fun loadLatestNews(): ResponseModel {
        return retrofitService.getLatestNews("us", BuildConfig.NEWS_API_KEY)
    }
}