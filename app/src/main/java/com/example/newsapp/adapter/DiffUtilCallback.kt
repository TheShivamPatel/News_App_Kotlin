package com.example.newsapp.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.newsapp.model.Article
import com.example.newsapp.model.News

class DiffUtilCallback : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}