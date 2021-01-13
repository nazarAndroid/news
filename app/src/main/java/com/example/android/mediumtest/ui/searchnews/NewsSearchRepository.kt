package com.example.android.mediumtest.ui.searchnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.android.mediumtest.common.Api
import com.example.android.mediumtest.common.Event
import com.example.android.mediumtest.common.NetworkService
import com.example.android.mediumtest.model.Result
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsSearchRepository {

    var api: Api = NetworkService.retrofitService("https://newsapi.org/v2/")
    private val newsDataLiveData = MutableLiveData<Event<Result>>()

    fun loadData(date:String,searchWord:String) {
        newsDataLiveData.postValue(Event.loading())
        api.getSearchNews(searchWord, date).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                newsDataLiveData.postValue(Event.error(Error(t)))
            }
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                val filteredList = response.body()?.articles?.filter { it.urlToImage!!.startsWith("http") }?.toList()
                filteredList?.let {
                    newsDataLiveData.postValue(Event.success(Result(ArrayList(it))))
                }
            }
        })
    }
    fun loadCategory(local:String, category:String) {
        newsDataLiveData.postValue(Event.loading())
        api.getCategoryNews(local, category).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {
                newsDataLiveData.postValue(Event.error(Error(t)))
            }
            override fun onResponse(
                call: Call<Result>,
                response: Response<Result>
            ) {
                val filteredList = response.body()?.articles?.filter { it.urlToImage!!.startsWith("http") }?.toList()
                filteredList?.let {
                    newsDataLiveData.postValue(Event.success(Result(ArrayList(it))))
                }
            }
        })
    }


    fun newsSearchData(): LiveData<Event<Result>> = newsDataLiveData


}