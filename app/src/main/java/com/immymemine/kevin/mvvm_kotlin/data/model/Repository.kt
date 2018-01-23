package com.immymemine.kevin.mvvm_kotlin.data.model

/**
 * Created by quf93 on 2018-01-23.
 */
class Repository {
    var id: Long = 0
    var name: String? = null
    var description: String? = null
    var forks: Int = 0
    var watchers: Int = 0
    var stars: Int = 0
    var language: String? = null
    var homepage: String? = null
    var owner: User? = null
    var isFork: Boolean = false
}