package com.ocbc.stackholders.networkimpl

import retrofit2.http.POST
import javax.inject.Singleton

interface NetworkServiceImpl {
    fun success(response : Any)
    fun error(errorMsg: String?)
    fun exception(t: Throwable?)
}