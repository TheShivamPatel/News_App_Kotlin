package com.example.newsapp.api

import com.example.newsapp.model.News
import com.example.newsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getTopNewsHeadline(@Query("country") country: String , @Query("page") page: Int) : Response<News>
//    https://newsapi.org/v2/top-headlines?country=in&apiKey=e3b06f4dba18478f9b4a7a4bed345588

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getNewsOnCategory(@Query("country") country: String, @Query("category") category: String , @Query("page") page: Int) : Response<News>

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getNewsOnSearch(@Query("q") q: String , @Query("page") page: Int) : Response<News>


//    https://newsapi.org/v2/everything? + apiKey=e3b06f4dba18478f9b4a7a4bed345588 + country=in + category
//    https://newsapi.org/v2/top-headlines?apiKey=e3b06f4dba18478f9b4a7a4bed345588&country=in&category=sports

}