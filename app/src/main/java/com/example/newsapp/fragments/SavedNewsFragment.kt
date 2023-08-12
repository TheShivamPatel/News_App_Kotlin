package com.example.newsapp.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.allnewsadapter.AllNewsAdapter
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.MainViewModel

class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    private lateinit var viewModel: MainViewModel
    private lateinit var saved_rv: RecyclerView
    lateinit var saved_adapter: AllNewsAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel = (activity as MainActivity).viewModel as MainViewModel

        saved_rv = view.findViewById(R.id.saved_rv)
        setUpRecyclerView(activity as MainActivity)

        viewModel.getAllArticle().observe(viewLifecycleOwner, Observer {article->
            saved_adapter.submitList(article)
        })


    }

    private fun setUpRecyclerView(context: Context) {
        saved_adapter = AllNewsAdapter(context, ::onItemClicked, ::onSaveBtn , 2)
        saved_rv.apply {
            adapter = saved_adapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun onItemClicked(article: Article) {
        val uri = Uri.parse(article.url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun onSaveBtn(article: Article) {
        viewModel.deleteSavedArticle(article)
    }

}