package com.immymemine.kevin.mvvm_kotlin.ui.view_model

import android.content.Context
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.immymemine.kevin.mvvm_kotlin.R
import com.immymemine.kevin.mvvm_kotlin.data.Injector
import com.immymemine.kevin.mvvm_kotlin.data.model.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

/**
 * Created by quf93 on 2018-01-23.
 */

class MainViewModel(var context : Context?, var dataListener: DataListener?) : ViewModel {

    // properties manage
    var infoMessageVisibility : ObservableInt
    var progressVisibility : ObservableInt
    var recyclerViewVisibility : ObservableInt
    var searchButtonVisibility : ObservableInt
    var infoMessage : ObservableField<String>

    var disposable : DisposableObserver<List<Repository>>? = null
    var repositories : List<Repository>? = null

    lateinit var editTextUsernameValue : String

    init {
        infoMessageVisibility = ObservableInt(View.VISIBLE)
        progressVisibility = ObservableInt(View.INVISIBLE)
        recyclerViewVisibility = ObservableInt(View.INVISIBLE)
        searchButtonVisibility = ObservableInt(View.INVISIBLE)
        infoMessage = ObservableField<String>(context?.getString(R.string.default_info))

    }

    fun onClickSearch(view: View) {
        loadRepositories(editTextUsernameValue)
    }

    fun onSearchAction(view: TextView, actionId: Int, event: KeyEvent): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            val username = view.text.toString()
            if (username.isNotEmpty())
                loadRepositories(username)
            return true
        }
        return false
    }

    fun getUsernameEditTextWatcher() : TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                editTextUsernameValue = s.toString()
                searchButtonVisibility.set(if(s?.length!! > 0) View.VISIBLE else View.GONE)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
    }

    private fun loadRepositories(username : String) {
        progressVisibility.set(View.VISIBLE)
        recyclerViewVisibility.set(View.INVISIBLE)
        infoMessageVisibility.set(View.INVISIBLE)

        val injector = Injector()
        val api = injector.provideApi()

        disposable = api.getRepositories(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(OperationObserver())
    }

    override fun destroy() {
        if(!disposable!!.isDisposed) disposable!!.dispose()
        disposable = null
        context = null
        dataListener = null
    }

    interface DataListener {
        fun onRepositoriesChanged(repositories : List<Repository>)
    }

    private inner class OperationObserver : DisposableObserver<List<Repository>>() {
        override fun onNext(value: List<Repository>?) {
            this@MainViewModel.repositories = value
        }

        override fun onError(e: Throwable?) {
            progressVisibility.set(View.INVISIBLE)
            infoMessage.set("Oops, Octocat doesn't know that username")
            infoMessageVisibility.set(View.VISIBLE)
        }

        override fun onComplete() {
            dataListener?.onRepositoriesChanged(repositories!!)
            progressVisibility.set(View.INVISIBLE)
            if(!repositories?.isEmpty()!!) {
                recyclerViewVisibility.set(View.VISIBLE)
            } else {
                infoMessage.set("This account doesn't have any public repository")
                infoMessageVisibility.set(View.VISIBLE)
            }
        }
    }
}