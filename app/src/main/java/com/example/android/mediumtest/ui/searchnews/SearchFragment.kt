package com.example.android.mediumtest.ui.searchnews

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.android.mediumtest.R
import com.example.android.mediumtest.common.Status
import com.example.android.mediumtest.model.Result
import com.example.android.mediumtest.ui.activity.MainActivity
import com.example.android.mediumtest.ui.newsList.NewsListAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SearchFragment : Fragment(), View.OnClickListener {
    private lateinit var viewModel: SearchViewModel

    private var allNewsRecyclerAdapter: NewsListAdapter = NewsListAdapter()

    @RequiresApi(Build.VERSION_CODES.O)
    var currentDateTime = LocalDateTime.now()
    var dateToday: String = String()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search_news_recycler.adapter = allNewsRecyclerAdapter
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)
        dateToday = currentDateTime.format(DateTimeFormatter.ISO_DATE)

        search_text.text.toString()

        updateData()
        search.setOnClickListener {
            if (search_text.text.length != null) {
                viewModel.loadSearchNews(dateToday, search_text.text.toString())
                clearAllCard()
            }
        }
        viewModel.loadCategoryNews((activity as MainActivity).locale, "health")

        viewModel.newsSearchData()

        health_card.setOnClickListener(this)
        sport_card.setOnClickListener(this)
        tehnology_card.setOnClickListener(this)
        science_card.setOnClickListener(this)
    }

    fun setData(data: Result?) {
        allNewsRecyclerAdapter.setAllNews(data?.articles!!)
    }

    private fun updateData() {
        viewModel.newsSearchData()
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer { event ->
                when (event.status) {
                    Status.LOADING -> {
                        progressLoadSearch.visibility = View.VISIBLE
                        allNewsRecyclerAdapter.setAllNews(arrayListOf())
                    }
                    Status.SUCCESS -> {
                        progressLoadSearch.visibility = View.INVISIBLE
                        setData(event.data)
                    }
                    Status.ERROR -> {
                    }
                }
            })
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.health_card -> {
                clearAllCard()
                makeCardSelected(health_card, health_image)
                viewModel.loadCategoryNews((activity as MainActivity).locale, "health")
            }
            R.id.sport_card -> {
                clearAllCard()
                makeCardSelected(sport_card, sport_image)
                viewModel.loadCategoryNews((activity as MainActivity).locale, "sports")
            }
            R.id.tehnology_card -> {
                clearAllCard()
                makeCardSelected(tehnology_card, tehnology_image)
                viewModel.loadCategoryNews((activity as MainActivity).locale, "technology")
            }
            R.id.science_card -> {
                clearAllCard()
                makeCardSelected(science_card, science_image)
                viewModel.loadCategoryNews((activity as MainActivity).locale, "science")
            }

        }
    }

    private fun clearAllCard() {
        health_card.setCardBackgroundColor(Color.WHITE)
        sport_card.setCardBackgroundColor(Color.WHITE)
        tehnology_card.setCardBackgroundColor(Color.WHITE)
        science_card.setCardBackgroundColor(Color.WHITE)

        health_image.setColorFilter(Color.BLACK)
        sport_image.setColorFilter(Color.BLACK)
        tehnology_image.setColorFilter(Color.BLACK)
        science_image.setColorFilter(Color.BLACK)
    }

    private fun makeCardSelected(v: CardView, image: ImageView) {
        v.setCardBackgroundColor(Color.parseColor("#FFFFF1F1"))
        image.setColorFilter(Color.parseColor("#FB6662"))
    }
}