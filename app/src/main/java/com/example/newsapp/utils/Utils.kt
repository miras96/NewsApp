package com.example.newsapp.utils

import java.text.SimpleDateFormat

object Utils {
    fun parseDate(dateString: String): String? {
        val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date = format.parse(dateString)
        format.applyLocalizedPattern("dd MMMM, yyyy")
        return format.format(date!!)
    }
}