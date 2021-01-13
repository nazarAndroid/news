package com.example.android.mediumtest.common

import com.example.android.mediumtest.model.Constants
import com.example.android.mediumtest.model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("top-headlines?pageSize=100")
    fun getAllNews(
        @Query("country") local: String,
        @Query("apiKey") key: String = Constants.API_KEY
    ): Call<Result>

    @GET("top-headlines")
    fun getCategoryNews(
        @Query("country") local: String,
        @Query("category") category: String,
        @Query("apiKey") key: String = Constants.API_KEY
    ): Call<Result>

    @GET("everything")
    fun getSearchNews(
        @Query("q") q: String,
        @Query("from") from: String,
        @Query("sortBy") sortBy: String = "popularity",
        @Query("apiKey") key: String = Constants.API_KEY
    ): Call<Result>

}