package com.example.newsapp.allnewsadapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.adapter.DiffUtilCallback
import com.example.newsapp.fragments.SavedNewsFragment
import com.example.newsapp.model.Article


class AllNewsAdapter(
    private val acontext: Context,
    private val onItemClicked: (Article) -> Unit,
    private val onSaveBtn: (Article) -> Unit,
    private val callFrom : Int
) :
    ListAdapter<Article, AllNewsAdapter.newViewHolder>(DiffUtilCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): newViewHolder {
        return newViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout_all, parent, false)
        )

    }

    override fun onBindViewHolder(holder: newViewHolder, position: Int) {
        val article = currentList[position]
        getItem(position).let {
            holder.apply {

                if(callFrom == 2){
                  save_image_btn.setImageResource(R.drawable.baseline_delete_24)
                }

                news_headline.text = it.title
                news_source_txt.text = it.source.name

                if (it.urlToImage == null){
                    news_image.setImageResource(R.drawable.empty_image_holder)
                }else{
                    Glide.with(acontext).load(it.urlToImage).thumbnail(Glide.with(acontext).load(R.drawable.loading)).into(news_image);
                }
                ll.setOnClickListener {
                    onItemClicked(article)
                }

                save_image_btn.setOnClickListener {
                    onSaveBtn(article)
                }

                share_image_btn.setOnClickListener {
                    val sendIntent : Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, "${article.title} - Download IndianNews app for more such content.")
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    acontext.startActivity(shareIntent)
                }

            }
        }
    }

    inner class newViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val news_image: ImageView = view.findViewById(R.id.news_image)
        val save_image_btn: ImageView = view.findViewById(R.id.save_image_btn)
        val share_image_btn: ImageView = view.findViewById(R.id.share_image_btn)
        val news_headline: TextView = view.findViewById(R.id.news_headline)
        val news_source_txt: TextView = view.findViewById(R.id.news_source_txt)
        val ll : LinearLayout = view.findViewById(R.id.ll)

    }


}