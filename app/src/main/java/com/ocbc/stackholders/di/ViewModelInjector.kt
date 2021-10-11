package com.ocbc.stackholders.di

import com.ocbc.stackholders.networkimpl.APIServices
import com.ocbc.stackholders.ui.dashboard.home.HomeViewModel
import com.ocbc.stackholders.ui.dashboard.payee.PayeeViewModel
import com.ocbc.stackholders.ui.dashboard.transfer.TransferViewModel
import com.ocbc.stackholders.ui.login.LoginViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(RepositoryModule::class)])
interface ViewModelInjector {
    fun inject(loginViewModel: LoginViewModel)
    fun inject(homeViewModel: HomeViewModel)
    fun inject(payeeViewModel: PayeeViewModel)
    fun inject(transferViewModel: TransferViewModel)
    fun inject(apiServices: APIServices)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector
        fun networkModule(networkModule: RepositoryModule): Builder
    }
}