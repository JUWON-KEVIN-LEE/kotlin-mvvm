package com.immymemine.kevin.mvvm_kotlin.data

import com.immymemine.kevin.mvvm_kotlin.data.model.Repository
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by quf93 on 2018-01-23.
 */
interface InterfaceApi {

    @GET("user/{username}/repos")
    fun getRepositories(@Path("username") username : String) : Observable<List<Repository>>
}