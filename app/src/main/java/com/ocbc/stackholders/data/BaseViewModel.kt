package com.ocbc.stackholders.data

import androidx.lifecycle.ViewModel
import com.ocbc.stackholders.di.DaggerViewModelInjector
import com.ocbc.stackholders.di.RepositoryModule
import com.ocbc.stackholders.di.ViewModelInjector
import com.ocbc.stackholders.ui.dashboard.home.HomeViewModel
import com.ocbc.stackholders.ui.dashboard.payee.PayeeViewModel
import com.ocbc.stackholders.ui.dashboard.transfer.TransferViewModel
import com.ocbc.stackholders.ui.login.LoginViewModel

abstract class BaseViewModel : ViewModel() {
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(RepositoryModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is LoginViewModel -> injector.inject(this)
            is HomeViewModel -> injector.inject(this)
            is PayeeViewModel -> injector.inject(this)
            is TransferViewModel -> injector.inject(this)
        }
    }
}