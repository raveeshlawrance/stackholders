package com.ocbc.stackholders.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ocbc.stackholders.data.LoginDataSource
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.networkimpl.APIServices
import com.ocbc.stackholders.networkimpl.NetworkServiceImpl
import com.ocbc.stackholders.networkimpl.RetrofitBuilder
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.util.Constants
import dagger.Module

import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
object RepositoryModule {

    @Provides
    internal fun provideRepository(apiServices: APIServices): LoginRepository {
        return LoginRepository(apiServices)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideApiServices(retrofit: Retrofit): APIServices {
        return retrofit.create(APIServices::class.java)
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRetrofitInterface(): Retrofit {
        return RetrofitBuilder.getRetrofit()
    }
}
