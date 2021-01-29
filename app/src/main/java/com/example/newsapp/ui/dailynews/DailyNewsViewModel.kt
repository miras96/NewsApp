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
        bookmarksList = repository.getArticles()
        emit(repository.loadLatestNews())
    }

    private lateinit var bookmarksList: List<Article>

    private val bookmarks = liveData<List<Article>> {
        latestNews.switchMap {
            liveData {
                emit(repository.getArticles())
            }
        }
    }

    fun getLatestNews() = latestNews
    fun getBookmarks() = bookmarksList

    fun saveToBookmarks(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val id = repository.saveArticle(article)
        if (id >= 0) _status.postValue(true)
    }
}