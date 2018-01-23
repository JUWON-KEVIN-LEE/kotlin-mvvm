package com.immymemine.kevin.mvvm_kotlin.ui

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.inputmethod.InputMethodManager
import com.immymemine.kevin.mvvm_kotlin.R
import com.immymemine.kevin.mvvm_kotlin.data.model.Repository
import com.immymemine.kevin.mvvm_kotlin.databinding.ActivityMainBinding
import com.immymemine.kevin.mvvm_kotlin.ui.view_model.MainViewModel

class MainActivity : AppCompatActivity(), MainViewModel.DataListener {

    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mainViewModel = MainViewModel(this, this)
        binding.viewModel = mainViewModel
        initiateRecyclerView(binding.recyclerView)
    }

    override fun onRepositoriesChanged(repositories: List<Repository>) {
        val adapter = binding.recyclerView.adapter as RepositoryAdapter // type variance
        adapter.setRepositories(repositories)
        adapter.notifyDataSetChanged()
        hideSoftKeyboard()
    }

    override fun onDestroy() {
        super.onDestroy()
        mainViewModel.destroy()
    }

    private fun initiateRecyclerView(recyclerView: RecyclerView) {
        val adapter = RepositoryAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun hideSoftKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.textSearch.windowToken, 0)
    }
}
