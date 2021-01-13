package com.example.android.mediumtest.ui.newsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.mediumtest.R
import com.example.android.mediumtest.common.LiveDataNetworkStatus
import com.example.android.mediumtest.common.Status
import com.example.android.mediumtest.model.Countries
import com.example.android.mediumtest.model.Result
import com.example.android.mediumtest.ui.activity.MainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import java.io.IOException


class NewsListFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private var allNewsRecyclerAdapter: NewsListAdapter = NewsListAdapter()
    private var allCountryAdapter: CountryAdapter? = null
    var snackbar:Snackbar? = null
    var changeLocal:String = String()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbar = Snackbar.make(view, "Error Connection. Try again later", Snackbar.LENGTH_INDEFINITE)

        val mLayoutManager = LinearLayoutManager(activity)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        Countries.getAllCountries().find {
            it.abbreviatedCountry == (activity as MainActivity).locale
        }.let {
            it?.isSelected = true
            changeLocal = it?.abbreviatedCountry!!
            Countries.getAllCountries().remove(it)
            Countries.getAllCountries().add(0, it!!)
        }
        LiveDataNetworkStatus(requireContext()).observe(viewLifecycleOwner, Observer {
            if(it){
                viewModel.loadNews(changeLocal)
                snackbar?.dismiss()
            }
            else{
                snackbar?.show()
            }
        })

        all_news_recycler.adapter = allNewsRecyclerAdapter

        allCountryAdapter = CountryAdapter(object : CountryAdapter.CountryListener {
            override fun onCountrySelected(position: Int) {
                val index: Int
                Countries.getAllCountries().find {
                    it.isSelected
                }?.let {
                    index = Countries.getAllCountries().indexOf(it)
                    it.isSelected = false
                    allCountryAdapter?.notifyItemChanged(index)
                }
                all_news_recycler.adapter = allNewsRecyclerAdapter

                Countries.getAllCountries()[position].isSelected = true
                changeLocal = Countries.getAllCountries()[position].abbreviatedCountry
                viewModel.loadNews(changeLocal)
                allCountryAdapter?.notifyItemChanged(position)
            }
        })

        country_recycler.adapter = allCountryAdapter
        allCountryAdapter?.setAllCountry(Countries.getAllCountries())
        country_recycler.layoutManager = mLayoutManager

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        updateData()

        viewModel.loadNews((activity as MainActivity).locale)
    }

    fun setData(data: Result?) {
        allNewsRecyclerAdapter.setAllNews(data?.articles!!)
    }


    private fun updateData(){
        //підписується на livedata
        viewModel.newsData().observe(viewLifecycleOwner, Observer { event ->
            when (event.status) {
                Status.LOADING -> {
                    progressLoad.visibility = View.VISIBLE
                    allNewsRecyclerAdapter.setAllNews(arrayListOf())
                }
                Status.SUCCESS -> {
                    progressLoad.visibility = View.INVISIBLE
                    setData(event.data)
                }
                Status.ERROR -> {
                }
            }
        })
    }

}