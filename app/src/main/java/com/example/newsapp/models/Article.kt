package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("source") val source: SourceModel,
    @SerializedName("author") val author: String,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String,
    @SerializedName("publishedAt") val publishedAt: String
)