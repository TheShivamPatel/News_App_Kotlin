package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.api.ApiService
import com.example.newsapp.api.RetrofitHelper
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.db.ArticleDatabase
import com.example.newsapp.fragments.HomeFragment
import com.example.newsapp.fragments.SavedNewsFragment
import com.example.newsapp.fragments.SearchNewsFragment
import com.example.newsapp.repository.Repository
import com.example.newsapp.viewmodel.MainViewModel
import com.example.newsapp.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())


        val database = ArticleDatabase.getInstance(this)
        val apiService = RetrofitHelper().retrofit.create(ApiService::class.java)
        val repository = Repository(apiService , database)
        viewModel = ViewModelProvider(this , ViewModelFactory(repository))[MainViewModel::class.java]


        binding.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){

                R.id.home -> replaceFragment(HomeFragment())
                R.id.search -> replaceFragment(SearchNewsFragment())
                R.id.save -> replaceFragment(SavedNewsFragment())

                else -> {false}

            }
            true

        }

    }


    private fun replaceFragment(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_layout, fragment)
        fragmentTransaction.commit()
    }

}