package com.example.android.mediumtest.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.media.tv.TvContract.Programs.Genres.NEWS
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.mediumtest.R
import com.example.android.mediumtest.model.News
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_open_news.*
import java.text.SimpleDateFormat
import java.util.*

class OpenNews : AppCompatActivity() {
    var news: News? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_news)

        news = intent.extras?.getParcelable<News>(NEWS)

        return_button.setOnClickListener {
            finish()
        }

        setAllText()

        readMore.setOnClickListener {
            val uri = Uri.parse("googlechrome://navigate?url="+ news?.url)
            val i = Intent(Intent.ACTION_VIEW, uri)
            if (i.resolveActivity(this@OpenNews.packageManager) == null) {
                i.data = Uri.parse("https://stackoverflow.com/")
            }
            this@OpenNews.startActivity(i)
        }
    }
    @SuppressLint("SimpleDateFormat")
    fun setAllText(){
        title_news.text = news?.title
        Picasso.get().load(news?.urlToImage).into(news_image)
        description_news.text = news?.description
        author_news.text = news?.author
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val output = SimpleDateFormat("dd.MM.yyyy   HH:mm")
        val d: Date = sdf.parse(news?.publishedAt)
        val formattedTime: String = output.format(d)
        date_news.text = formattedTime

    }
}