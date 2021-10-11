package com.ocbc.stackholders.ui.dashboard.transfer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.payee.DataItem
import com.ocbc.stackholders.data.payee.PayeeViewModelFactory
import com.ocbc.stackholders.databinding.FragmentTransferBinding
import com.ocbc.stackholders.ui.BaseActivity
import com.ocbc.stackholders.ui.dashboard.payee.PayeeViewModel
import com.ocbc.stackholders.util.Constants
import com.ocbc.stackholders.util.Utility

class TransferActivity : BaseActivity<FragmentTransferBinding>(), BaseActivity.DatePickerListener {

    private lateinit var payeeViewModel: PayeeViewModel
    lateinit var datePickerListener : DatePickerListener
    lateinit var transferActivity: TransferActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        payeeViewModel = ViewModelProvider(this, PayeeViewModelFactory()).get(PayeeViewModel::class.java)
        datePickerListener = this
        transferActivity = this
        binding.title.text = "Make Transfer"
        var dataItem : DataItem? = intent?.getParcelableExtra("keyName")
        binding.txtViewDate.text = Utility.getCurrentDate(System.currentTimeMillis(), Constants.PAYMENT_DATE_FORMAT)
        payeeViewModel.getPaymentDate()
        payeeViewModel.payment_date.observe(this, Observer {
            binding.txtViewDate.text = it
        })
        binding.txtViewDate.setOnClickListener(View.OnClickListener {
            showDatePicker(datePickerListener)
        })

        binding.btnSubmitTransfer.setOnClickListener(View.OnClickListener {
            payeeViewModel.submitPayment()
        })
        payeeViewModel.setPayeeInfo(dataItem)
        payeeViewModel.payment_source_acc_detial.observe(this, Observer {
            binding.txtViewRecipientName.text = it?.accountHolderName
            binding.txtViewRecipientNo.text = it?.accountNo
        })

        binding.txtViewDescription.afterTextChanged {
            payeeViewModel.setPayeeDesc(it)
        }

        binding.btnTxnCancel.setOnClickListener {
            finish()
        }
        binding.txtViewAmount.afterTextChanged {
            payeeViewModel.setPayeeAmount(it)
        }

        payeeViewModel.paymentResponse.observe(this, Observer {
            val paymentResult = it ?: return@Observer

            if (paymentResult.error != null) {
                showAlert("Something wrong", paymentResult.error)
            }
            if (paymentResult.success != null) {
                showAlert("Success", paymentResult.success.data?.description)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                var dataItem : DataItem? = data?.getParcelableExtra("keyName")
                payeeViewModel.setPayeeDetails(dataItem)
            }
        }
    }

    override fun onPickDate(selectedDate: String) {
        payeeViewModel.setPaymentDate(selectedDate)
    }

    override fun layoutId(): Int {
        return R.layout.fragment_transfer
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })

}