package com.ocbc.stackholders.data.payee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocbc.stackholders.ui.dashboard.payee.PayeeViewModel

class PayeeViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PayeeViewModel::class.java)) {
            return PayeeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}