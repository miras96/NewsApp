package com.example.newsapp.di

import com.example.newsapp.repository.Repository
import com.example.newsapp.repository.retrofit.RetrofitClient
import com.example.newsapp.repository.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitService() = RetrofitClient.getClient().create(RetrofitService::class.java)!!

    @Singleton
    @Provides
    fun provideRepository(retrofitService: RetrofitService) = Repository(retrofitService)
}