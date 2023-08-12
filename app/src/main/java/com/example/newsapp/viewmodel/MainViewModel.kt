package com.example.newsapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.model.Article
import com.example.newsapp.model.News
import com.example.newsapp.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {


    val liveData: LiveData<News>
        get() = repository.liveData


    val categoryLiveData: LiveData<News>
        get() = repository.categoryLiveData


    private val optionsObject = MutableLiveData<String>("general")

    val optionLive : LiveData<String>
        get() = optionsObject


    fun updateOption(string: String){
        optionsObject.value = string
    }

    suspend fun getOptionNews(q : String) {
        return repository.getCatagorialNews(q)
    }


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTopNews()
        }
    }



    // on search

        val searchLiveData: LiveData<News>
        get() = repository.liveNewsOnSearch


    fun getNewsOnSearch(q : String) = viewModelScope.launch {
        repository.searchNews(q)
    }



    // database

    fun saveArticle(article: Article) = viewModelScope.launch {
        repository.insertArticle(article)
    }

    fun deleteSavedArticle(article: Article) = viewModelScope.launch {
        repository.deleteArticle(article)
    }

    fun getAllArticle() = repository.getArticleFromDatabase()



}