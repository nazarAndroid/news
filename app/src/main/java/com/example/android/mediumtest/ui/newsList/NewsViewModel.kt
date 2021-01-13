package com.example.android.mediumtest.ui.newsList

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.mediumtest.common.Event
import com.example.android.mediumtest.model.Result


class NewsViewModel : ViewModel() {
    private val repository: NewsRepository =
        NewsRepository()
    fun loadNews(local:String) {
        repository.loadData(local)
    }
    fun newsData(): LiveData<Event<Result>> = repository.newsData()
}