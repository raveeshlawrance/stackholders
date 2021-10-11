package com.ocbc.stackholders.ui.dashboard.payee

import androidx.lifecycle.MutableLiveData
import com.ocbc.stackholders.data.BaseViewModel
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.data.payee.*
import com.ocbc.stackholders.networkimpl.NetworkServiceImpl
import com.ocbc.stackholders.util.Constants
import com.ocbc.stackholders.util.Utility
import javax.inject.Inject

class PayeeViewModel : BaseViewModel() {

    @Inject
    lateinit var loginRepository : LoginRepository

    private val _index = MutableLiveData<Int>()
    val _text = MutableLiveData<PayeeListResponse>()

    val paymentResponse = MutableLiveData<PaymentStatusResult>()
    val payment_date = MutableLiveData<String>()
    val payment_desc = MutableLiveData<String>()
    val payment_amount = MutableLiveData<String>()
    val payment_source_acc_detial = MutableLiveData<DataItem>()
    lateinit var payment_server_date : String
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

    fun submitPayment() {
        if(payment_amount?.value == null || payment_amount.value!!.toDouble() == 0.0) {
            paymentResponse.value = PaymentStatusResult(error = "Please enter more than 0 SGD")
            return
        } else if(payment_desc?.value == null || payment_desc.value.equals("") ) {
            paymentResponse.value = PaymentStatusResult(error = "Please enter payment description to process your request")
            return
        }
        var paymentRequest = PaymentRequest(payment_server_date, payment_amount.value?.toDouble(), getAccountNo(), payment_desc.value)
        loginRepository.submitPayment(paymentRequest, object: NetworkServiceImpl {
            override fun success(response: Any) {
                var serverResponse = response as PaymentResponse
                paymentResponse.value = PaymentStatusResult(success = PaymentResponse(data = serverResponse.data))
            }

            override fun error(errorMsg: String?) {
                paymentResponse.value = PaymentStatusResult(error = errorMsg)
            }

            override fun exception(t: Throwable?) {
                paymentResponse.value = PaymentStatusResult(error = "Server is not respondng right now. Please try again later")
            }

        })
    }

    fun getPaymentDate() {
        payment_date.value = "Immediately"
        payment_server_date = Utility.covertDateToServerDate(Utility.getCurrentDate(System.currentTimeMillis(), Constants.PAYMENT_DATE_FORMAT))
    }
    fun setPaymentDate(selectedDate: String) {
        payment_date.value = Utility.covertToUIDateFormat(selectedDate, Constants.PAYMENT_DATE_FORMAT)
        payment_server_date = Utility.covertDateToServerDate(selectedDate)
    }

    fun setPayeeDetails(selectedAccount: DataItem?) {
        payment_source_acc_detial.value = selectedAccount!!
    }

    fun setPayeeDesc(payeeDesc: String) {
        payment_desc.value = payeeDesc
    }

    fun setPayeeAmount(payeeAmount: String) {
        payment_amount.value = payeeAmount
    }

    private fun getAccountNo() : String? {
        return payment_source_acc_detial.value?.accountNo
    }

    fun setPayeeInfo(dataItem: DataItem?) {
        payment_source_acc_detial.value = dataItem!!
    }
}