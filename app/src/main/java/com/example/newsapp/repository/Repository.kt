package com.example.newsapp.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.example.newsapp.BuildConfig
import com.example.newsapp.R
import com.example.newsapp.models.Article
import com.example.newsapp.repository.retrofit.RetrofitService
import com.example.newsapp.repository.room.ArticleDao
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val articleDao: ArticleDao,
    private val sharedPreferences: SharedPreferences,
    private val context: Context
) {
    suspend fun loadLatestNews() = retrofitService.getLatestNews(
        getCountryCode(),
        BuildConfig.NEWS_API_KEY
    )

    suspend fun getArticles() = articleDao.getAllArticles()

    suspend fun saveArticle(article: Article): Long {
        articleDao.getArticle(article.url)?.let {
            return it.id.toLong()
        }
        return articleDao.insert(article)
    }

    suspend fun deleteArticle(article: Article) = articleDao.delete(article)

    fun getCountryCode() = sharedPreferences.getString(
        context.getString(R.string.country_key),
        context.getString(R.string.country_default_value)
    ) ?: context.getString(R.string.country_default_value)

    fun setCountryCode(country: String) {
        sharedPreferences.edit {
            putString(context.getString(R.string.country_key), country)
        }
    }
}