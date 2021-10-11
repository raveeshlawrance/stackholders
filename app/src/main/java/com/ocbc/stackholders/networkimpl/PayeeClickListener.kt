package com.ocbc.stackholders.networkimpl

import com.ocbc.stackholders.data.payee.DataItem

interface PayeeClickListener {
    fun onClickPayee(userListResponseItem: DataItem?)
}