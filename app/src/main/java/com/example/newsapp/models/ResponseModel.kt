package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: ArrayList<Article>
)