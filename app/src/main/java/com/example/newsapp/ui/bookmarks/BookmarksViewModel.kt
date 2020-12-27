package com.example.newsapp.ui.bookmarks

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.newsapp.models.Article
import com.example.newsapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookmarksViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val savedBookmarks = liveData<List<Article>>(Dispatchers.IO) {
        val articles = repository.getArticles()
        emit(articles)
    }

    fun getSavedBookmarks() = savedBookmarks

    fun removeFromBookmarks(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        val numberOfDeletedItems = repository.deleteArticle(article)
        _status.postValue(true)
    }
}