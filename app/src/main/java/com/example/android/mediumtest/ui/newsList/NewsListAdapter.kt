package com.example.android.mediumtest.ui.newsList

import android.content.Intent
import android.media.tv.TvContract.Programs.Genres.NEWS
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mediumtest.R
import com.example.android.mediumtest.model.News
import com.example.android.mediumtest.ui.activity.OpenNews
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class NewsListAdapter() : RecyclerView.Adapter<NewsListAdapter.NewsViewHolder>() {

    private var all_news_array: ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.card_news, parent, false)
        return NewsViewHolder(
            view
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(all_news_array[position])
    }

    override fun getItemCount(): Int {
        return all_news_array.size
    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var news: News?
        private var oneNews: CardView
        private var titleNews: TextView
        private var descriptionNews: TextView
        private var dateNews: TextView
        private var photoNews: ImageView

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(news: News) {
            this.news = news
            titleNews.text = news.title
            descriptionNews.text = news.description
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val output = SimpleDateFormat("dd.MM.yyyy   HH:mm")
            val d: Date = sdf.parse(news.publishedAt)
            val formattedTime: String = output.format(d)
            dateNews.text = formattedTime
            Picasso.get().load(news.urlToImage).into(photoNews)
            oneNews.setOnClickListener {
                val intent = Intent(itemView.context, OpenNews::class.java)
                intent.putExtra(NEWS, news)
                startActivity(itemView.context, intent, null)
            }
        }

        init {
            news = null
            oneNews = itemView.findViewById(R.id.one_news)
            titleNews = itemView.findViewById(R.id.title_news)
            descriptionNews = itemView.findViewById(R.id.description_news)
            dateNews = itemView.findViewById(R.id.date_news)
            photoNews = itemView.findViewById(R.id.photo_news)
        }

    }

    fun setAllNews(all_news: ArrayList<News>) {
        this.all_news_array = all_news
        notifyDataSetChanged()
    }


}