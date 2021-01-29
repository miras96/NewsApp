package com.example.newsapp.repository.room

import androidx.room.*
import com.example.newsapp.models.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    suspend fun getAllArticles(): List<Article>

    @Query("SELECT * FROM article WHERE url = :url")
    suspend fun getArticle(url: String): Article?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long

    @Delete
    suspend fun delete(article: Article): Int
}