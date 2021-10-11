package com.ocbc.stackholders.di

import com.ocbc.stackholders.ui.dashboard.DashboardActivity
import com.ocbc.stackholders.ui.dashboard.payee.PayeeFragment
import com.ocbc.stackholders.ui.dashboard.transfer.TransferActivity
import com.ocbc.stackholders.ui.login.LoginActivity
import dagger.Component

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(transferActivity: TransferActivity)
    fun inject(dashboardActivity: DashboardActivity)
}
