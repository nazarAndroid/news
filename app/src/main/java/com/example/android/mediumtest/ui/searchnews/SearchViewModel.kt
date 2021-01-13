package com.example.android.mediumtest.ui.searchnews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.android.mediumtest.common.Event
import com.example.android.mediumtest.model.Result

class SearchViewModel: ViewModel() {
    private val repository: NewsSearchRepository =
        NewsSearchRepository()
    fun loadSearchNews(searchWord:String, date:String) {
        repository.loadData(searchWord, date)
    }
    fun loadCategoryNews(local:String, category:String){
        repository.loadCategory(local, category)
    }

    fun newsSearchData(): LiveData<Event<Result>> = repository.newsSearchData()
}