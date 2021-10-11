package com.ocbc.stackholders.networkimpl

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.ui.StackHolderApplication
import com.ocbc.stackholders.util.Constants
import com.ocbc.stackholders.util.Constants.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject


object RetrofitBuilder {

    fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(120, TimeUnit.SECONDS )
        httpClient.connectTimeout(120, TimeUnit.SECONDS )
        httpClient.networkInterceptors().add(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Content-Type", "application/json")
            requestBuilder.header("Accept", "application/json")
            if(!requestBuilder.build().url().toString().contains("authenticate/login"))
                requestBuilder.header("Authorization", StackHolderApplication.getAppData(Constants.TOKEN))
            chain.proceed(requestBuilder.build())
        })
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}