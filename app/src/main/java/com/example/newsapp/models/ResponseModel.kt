package com.example.newsapp.models

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: ArrayList<Article>,
    @SerializedName("code") val code: String? = null,
    @SerializedName("message") val message: String? = null
) {
    companion object {
        enum class Status(val value: String) {
            OK("ok"), ERROR("error")
        }
        fun getPositiveStatus() =  Status.OK.value
        fun getNegativeStatus() = Status.ERROR.value
    }
}