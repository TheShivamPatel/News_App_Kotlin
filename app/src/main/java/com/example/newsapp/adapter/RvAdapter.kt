package com.example.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.model.Article

class RvAdapter(private val context : Context) : ListAdapter<Article , RvAdapter.viewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        return viewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout_topnews, parent, false))
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val article = currentList[position]
        getItem(position).let {
            holder.apply {
                top_headline.text = it.title
                if (it.urlToImage == null){
                    top_image.setImageResource(R.drawable.empty_image_holder)
                }else{
                    Glide.with(context).load(it.urlToImage).into(top_image);
                }
            }
        }
    }

    inner class viewHolder(view : View) : RecyclerView.ViewHolder(view){
        val top_image : ImageView = view.findViewById(R.id.top_news_image)
        val top_headline : TextView = view.findViewById(R.id.top_headline)
    }


}