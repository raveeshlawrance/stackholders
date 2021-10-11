package com.ocbc.stackholders.di

import android.app.Application
import android.content.Context
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.ui.StackHolderApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(stackHolderApplication: StackHolderApplication?)
    @get:ApplicationContext
    val context: Context?
    val application: Application?
    val stackHolderKeystore : StackHolderKeystore
}
