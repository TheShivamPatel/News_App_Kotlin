package com.example.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsapp.api.ApiService
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.model.Article
import com.example.newsapp.model.News

class Repository(private val apiService: ApiService , private val db : ArticleDatabase) {

    private val newsMutableLiveData = MutableLiveData<News>()
    val liveData: LiveData<News>
        get() = newsMutableLiveData

    suspend fun getTopNews() {

        val result = apiService.getTopNewsHeadline("in", 1)

        if (result.body() != null) {
            newsMutableLiveData.postValue(result.body())
        }

    }



    private val newCatagorialLiveData = MutableLiveData<News>()

    val categoryLiveData: LiveData<News>
        get() = newCatagorialLiveData


    suspend fun getCatagorialNews(category: String) {

        val result = apiService.getNewsOnCategory("in", category ,1)

        if (result.body() != null) {
            newCatagorialLiveData.postValue(result.body())
        }

    }


    // search news
    private val newsOnSearchObject = MutableLiveData<News>()
    val liveNewsOnSearch : LiveData<News>
        get() = newsOnSearchObject

    suspend fun searchNews(q : String){
        val result = apiService.getNewsOnSearch(q , 1)

        if (result.body() != null){
            newsOnSearchObject.postValue(result.body())
        }
    }



    // database
    suspend fun insertArticle(article: Article) = db.getArticleDao().upsert(article)

    fun getArticleFromDatabase() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)




}