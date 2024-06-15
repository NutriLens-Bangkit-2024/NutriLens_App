package com.capstone.nutrilens.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.response.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<News>()

    fun setNewsList(newsRespons: List<News>) {
        this.newsList.clear()
        this.newsList.addAll(newsRespons)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(newsList[position])
    }

    override fun getItemCount(): Int = newsList.size

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val newsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        private val newsSource: TextView = itemView.findViewById(R.id.tv_news_source)
        private val newsImage: ImageView = itemView.findViewById(R.id.iv_news)

        fun bind(news: News) {
            newsTitle.text = news.title
            newsSource.text = news.source
            Glide.with(itemView.context).load(news.image).into(newsImage)

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, NewsActivity::class.java)
                intent.putExtra("id", news.id)
                itemView.context.startActivity(intent)
            }
        }
    }
}

