package com.example.newsapp.ui.dailynews

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.newsapp.models.Article
import com.example.newsapp.models.ResponseModel
import com.example.newsapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DailyNewsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val latestNews = liveData<ResponseModel>(Dispatchers.IO) {
        emit(repository.loadLatestNews())
    }

    fun getLatestNews() = latestNews

    fun saveToBookmarks(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val id = repository.saveArticle(article)
        _status.postValue(true)
    }
}