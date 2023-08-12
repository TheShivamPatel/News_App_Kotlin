package com.example.newsapp.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapter.RvAdapter
import com.example.newsapp.allnewsadapter.AllNewsAdapter
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment(R.layout.fragment_search_news) {

    private lateinit var search: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchNewsAdapter: AllNewsAdapter
    private lateinit var viewModel: MainViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel as MainViewModel

        recyclerView = view.findViewById(R.id.searching_rv)
        search = view.findViewById(R.id.edt_search)

        setUpRecyclerView(activity as MainActivity)


        var job: Job? = null
        search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getNewsOnSearch(editable.toString())
                    }
                }
            }
        }


        viewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            if(it != null){
                searchNewsAdapter.submitList(it.articles)
            }
        })


    }


    private fun setUpRecyclerView(context: Context) {

        searchNewsAdapter = AllNewsAdapter(context, ::onItemClicked, ::onSaveBtn, 1)
        recyclerView.apply {
            adapter = searchNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun onItemClicked(article: Article) {
        val uri = Uri.parse(article.url)
        startActivity(Intent(Intent.ACTION_VIEW, uri))
    }

    private fun onSaveBtn(article: Article) {
        viewModel.saveArticle(article)
        Toast.makeText(context, "saved", Toast.LENGTH_SHORT).show()
    }


}