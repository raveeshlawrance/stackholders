package com.ocbc.stackholders.ui.dashboard.transfer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocbc.stackholders.data.BaseViewModel
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.data.model.AccountResponse
import com.ocbc.stackholders.data.payee.PayeeListResponse
import com.ocbc.stackholders.networkimpl.NetworkServiceImpl
import javax.inject.Inject

class TransferViewModel : BaseViewModel() {

    @Inject
    lateinit var loginRepository : LoginRepository

    private val _index = MutableLiveData<Int>()
    val _text = MutableLiveData<PayeeListResponse>()

    fun setIndex(index: Int) {
        _index.value = index
    }

    fun getPayeeList() {
        loginRepository.getPayeeList(object: NetworkServiceImpl {
            override fun success(response: Any) {
                var payeeListResponse : PayeeListResponse = response as PayeeListResponse
                _text.value = payeeListResponse
            }

            override fun error(errorMsg: String?) {
                TODO("Not yet implemented")
            }

            override fun exception(t: Throwable?) {
                TODO("Not yet implemented")
            }

        })
    }
}