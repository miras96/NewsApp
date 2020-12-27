package com.example.newsapp.repository

import com.example.newsapp.BuildConfig
import com.example.newsapp.models.Article
import com.example.newsapp.repository.retrofit.RetrofitService
import com.example.newsapp.repository.room.ArticleDao
import javax.inject.Inject

class Repository @Inject constructor(
    private val retrofitService: RetrofitService,
    private val articleDao: ArticleDao
) {
    suspend fun loadLatestNews() =
        retrofitService.getLatestNews("us", BuildConfig.NEWS_API_KEY)

    suspend fun getArticles() = articleDao.getAllArticles()

    suspend fun saveArticle(article: Article) = articleDao.insert(article)

    suspend fun deleteArticle(article: Article) = articleDao.delete(article)
}