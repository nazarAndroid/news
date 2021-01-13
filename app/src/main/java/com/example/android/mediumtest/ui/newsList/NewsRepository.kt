package com.example.android.mediumtest.ui.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.mediumtest.common.Api
import com.example.android.mediumtest.common.Event
import com.example.android.mediumtest.common.NetworkService
import com.example.android.mediumtest.model.News
import com.example.android.mediumtest.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {
    var api: Api = NetworkService.retrofitService("https://newsapi.org/v2/")
    private val newsDataLiveData = MutableLiveData<Event<Result>>()
    fun loadData(local: String) {
        newsDataLiveData.postValue(Event.loading())
        api.getAllNews(local).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                newsDataLiveData.postValue(Event.error(Error(t)))
            }

            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {

                val filteredList: List<News>? = response.body()?.articles?.filter { it.urlToImage?.startsWith("http")?:false }?.toList()
                filteredList?.let {
                    newsDataLiveData.postValue(Event.success(Result(articles = ArrayList(it))))
                }
            }
        })
    }

    fun newsData(): LiveData<Event<Result>> = newsDataLiveData


}