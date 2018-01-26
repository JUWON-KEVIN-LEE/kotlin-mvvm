package com.immymemine.kevin.mvvm_kotlin.ui

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.immymemine.kevin.mvvm_kotlin.R
import com.immymemine.kevin.mvvm_kotlin.data.model.Repository
import com.immymemine.kevin.mvvm_kotlin.databinding.ItemRepoBinding
import com.immymemine.kevin.mvvm_kotlin.ui.view_model.RepoViewModel
import java.util.*

/**
 * Created by quf93 on 2018-01-23.
 */
class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    private var repositories : List<Repository> = Collections.emptyList()

    fun setRepositories(repositories : List<Repository>) {
        this.repositories = repositories
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemRepoBinding>(LayoutInflater.from(parent?.context), R.layout.item_repo, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindRepository(repositories.get(position))
    }

    class ViewHolder(var binding : ItemRepoBinding) : RecyclerView.ViewHolder(binding.repositoryContainer) {

        internal fun bindRepository(repository : Repository) {
            if(binding.viewModel == null) {
                binding.viewModel = RepoViewModel(itemView.context, repository)
            } else {
                // binding 의 변수인 view model 의 repository 만 바꿔주면서 recycling
                RepoViewModel.setRepository(binding.viewModel!!, repository)
            }
        }
    }
}