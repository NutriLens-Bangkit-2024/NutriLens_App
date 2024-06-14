package com.capstone.nutrilens.ui.news

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.nutrilens.R
import com.capstone.nutrilens.data.response.News

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val newsList = mutableListOf<News>()

    fun setNewsList(newsRespons: List<News>) {
        this.newsList.clear()
        newsRespons?.let { this.newsList.addAll(it)}
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

        fun bind(news: News) {
            newsTitle.text = news.title
            newsSource.text = news.source
            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, NewsActivity::class.java).apply {
                    putExtra("id", news.id)
                }
                context.startActivity(intent)
            }
        }
    }
}
