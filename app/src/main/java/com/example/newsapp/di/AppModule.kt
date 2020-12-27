package com.example.newsapp.di

import android.content.Context
import com.example.newsapp.repository.Repository
import com.example.newsapp.repository.retrofit.RetrofitClient
import com.example.newsapp.repository.retrofit.RetrofitService
import com.example.newsapp.repository.room.AppDatabase
import com.example.newsapp.repository.room.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService() = RetrofitClient.getClient().create(RetrofitService::class.java)!!

    @Singleton
    @Provides
    fun provideRepository(retrofitService: RetrofitService,
                          articleDao: ArticleDao) =
        Repository(retrofitService, articleDao)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.getInstance(context)

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase) = appDatabase.articleDao()
}