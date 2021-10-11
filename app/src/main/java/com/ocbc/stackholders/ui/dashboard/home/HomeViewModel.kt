package com.ocbc.stackholders.ui.dashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ocbc.stackholders.data.BaseViewModel
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.data.model.AccountResponse
import com.ocbc.stackholders.data.transaction.TransactionHistoryResponse
import com.ocbc.stackholders.networkimpl.NetworkServiceImpl
import javax.inject.Inject

class HomeViewModel : BaseViewModel() {

    @Inject
    lateinit var loginRepository : LoginRepository

    val _text = MutableLiveData<AccountResponse>()
    val text: LiveData<AccountResponse> = _text
    val transactionList = MutableLiveData<TransactionHistoryResponse>()
    fun getBalances() {
        loginRepository.getBalances(object: NetworkServiceImpl {
            override fun success(response: Any) {
                var accountResponse : AccountResponse = response as AccountResponse
                _text.value = accountResponse
            }

            override fun error(errorMsg: String?) {
                TODO("Not yet implemented")
            }

            override fun exception(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })

    }


    fun getTransactionList() {
        loginRepository.getTransactionList(object: NetworkServiceImpl {
            override fun success(response: Any) {
                transactionList.value = response as TransactionHistoryResponse
            }

            override fun error(errorMsg: String?) {

            }

            override fun exception(t: Throwable?) {

            }

        })
        //

    }
}