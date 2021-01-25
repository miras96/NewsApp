package com.example.newsapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.newsapp.R
import com.example.newsapp.repository.Repository
import com.example.newsapp.repository.retrofit.RetrofitClient
import com.example.newsapp.repository.retrofit.RetrofitService
import com.example.newsapp.repository.room.AppDatabase
import com.example.newsapp.repository.room.ArticleDao
import com.example.newsapp.utils.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService(): RetrofitService = RetrofitClient.getClient().create(RetrofitService::class.java)

    @Singleton
    @Provides
    fun provideRepository(retrofitService: RetrofitService,
                          articleDao: ArticleDao,
                          sharedPreferences: SharedPreferences,
                          @ApplicationContext context: Context) =
        Repository(retrofitService, articleDao, sharedPreferences, context)

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "article")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase) = appDatabase.articleDao()

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences = context.getSharedPreferences(
        context.getString(R.string.preference_file_key),
        Context.MODE_PRIVATE
    )
}