package com.example.newsapp.utils

import android.content.Context
import com.example.newsapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    const val URL_KEY = "url"

    fun parseDate(dateString: String, context: Context): String {
        val format = SimpleDateFormat(context.getString(R.string.input_date_format), Locale.US)
        val date = try {
            format.parse(dateString)
        } catch (e: ParseException) { null }
        format.applyLocalizedPattern(context.getString(R.string.output_date_format))
        date?.let { return format.format(it) }
        return ""
    }
}