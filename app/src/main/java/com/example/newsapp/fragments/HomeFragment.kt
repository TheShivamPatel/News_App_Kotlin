package com.example.newsapp.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.adapter.RvAdapter
import com.example.newsapp.allnewsadapter.AllNewsAdapter
import com.example.newsapp.databinding.FragmentHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.viewmodel.MainViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var newsAdapter: RvAdapter
    lateinit var allNewsAdapter: AllNewsAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)


        mainViewModel = (activity as MainActivity).viewModel as MainViewModel

        binding.lifecycleOwner = this
        binding.mainModel = mainViewModel


        setUpRecyclerView(activity as MainActivity)

        mainViewModel.liveData.observe(viewLifecycleOwner, Observer {

            newsAdapter.submitList(it.articles)

        })


        mainViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            allNewsAdapter.submitList(it.articles)
        })


        mainViewModel.optionLive.observe(viewLifecycleOwner, Observer { my ->
            GlobalScope.launch {
                mainViewModel.getOptionNews(my)
            }
        })


        return binding.root
    }


    private fun setUpRecyclerView(context: Context) {

        newsAdapter = RvAdapter(context)
        binding.topNewsRv.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        allNewsAdapter = AllNewsAdapter(context, ::onItemClicked , ::onSaveBtn , 1)
        binding.allNewsRv.apply {
            adapter = allNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }

    }

    private fun onItemClicked(article: Article) {
        val uri = Uri.parse(article.url)
        startActivity(Intent(Intent.ACTION_VIEW , uri))
    }

    private fun onSaveBtn(article: Article){
        mainViewModel.saveArticle(article)
        Toast.makeText(context , "saved" , Toast.LENGTH_SHORT).show()
    }



}
