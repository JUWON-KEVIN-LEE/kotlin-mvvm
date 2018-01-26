package com.immymemine.kevin.mvvm_kotlin.ui.view_model

import android.content.Context
import android.databinding.BaseObservable
import android.view.View
import com.immymemine.kevin.mvvm_kotlin.R
import com.immymemine.kevin.mvvm_kotlin.data.model.Repository

/**
 * Created by quf93 on 2018-01-23.
 */
class RepoViewModel(var context : Context, var repository : Repository) : BaseObservable(), ViewModel {

    fun getName(): String? {
        return repository.name
    }

    fun getDescription(): String? {
        return repository.description
    }

    fun getStars(): String? {
        return context.getString(R.string.text_stars, repository.stars)
    }

    fun getWatchers(): String? {
        return context.getString(R.string.text_watchers, repository.watchers)
    }

    fun getForks(): String? {
        return context.getString(R.string.text_forks, repository.forks)
    }

    override fun destroy() {

    }

    fun onItemClick(view: View) {

    }

    companion object {
        fun setRepository(repoViewModel: RepoViewModel, repository: Repository) {
            repoViewModel.repository = repository
            repoViewModel.notifyChange()
        }
    }
}