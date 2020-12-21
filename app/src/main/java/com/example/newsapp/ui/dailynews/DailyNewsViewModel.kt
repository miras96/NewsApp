package com.example.newsapp.ui.dailynews

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.newsapp.models.ResponseModel
import com.example.newsapp.repository.Repository
import kotlinx.coroutines.Dispatchers

class DailyNewsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val latestNews = liveData<ResponseModel>(Dispatchers.IO) {
        emit(repository.loadLatestNews())
    }

    fun getLatestNews() = latestNews
}