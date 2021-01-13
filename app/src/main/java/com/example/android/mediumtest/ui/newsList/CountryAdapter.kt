package com.example.android.mediumtest.ui.newsList

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mediumtest.R
import com.example.android.mediumtest.model.Country

class CountryAdapter(private var listener: CountryListener) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    var countryArray: ArrayList<Country> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.card_country, parent, false)
        return CountryViewHolder(
            view, listener
        )
    }

    override fun getItemCount(): Int {
        return countryArray.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countryArray[position])
    }

    class CountryViewHolder(itemView: View, listener: CountryListener) :
        RecyclerView.ViewHolder(itemView) {

        var countryName: TextView = itemView.findViewById(R.id.country_name)
        var country: Country? = null

        fun bind(country: Country) {
            this.country = country
            countryName.setTextColor(Color.BLACK)
            if (country.isSelected) {
                countryName.setTextColor(Color.parseColor("#ff3333"))
            }
            countryName.text = country.countryName
        }

        init {
            countryName.setOnClickListener {
                listener.onCountrySelected(adapterPosition)
            }
        }
    }

    interface CountryListener {
        fun onCountrySelected(position: Int)
    }

    fun setAllCountry(allCounty: ArrayList<Country>) {
        this.countryArray = allCounty
        notifyDataSetChanged()
    }
}