package com.ocbc.stackholders.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.util.Constants
import dagger.Module

import dagger.Provides


@Module
class ApplicationModule(app: Application) {
    private val mApplication: Application = app

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return mApplication
    }

    @Provides
    fun provideApplication(): Application {
        return mApplication
    }

    @Provides
    fun provideKeyStore(sharedPreferences: SharedPreferences): StackHolderKeystore {
        return StackHolderKeystore(sharedPreferences)
    }

    @Provides
    fun provideSharedPrefs(): SharedPreferences {
        return mApplication.getSharedPreferences(Constants.STACK_HOLDER_KEY_FILE, Context.MODE_PRIVATE)
    }

}
