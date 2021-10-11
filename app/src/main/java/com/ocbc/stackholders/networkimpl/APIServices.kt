package com.ocbc.stackholders.networkimpl

import com.ocbc.stackholders.data.LoginUserRequest
import com.ocbc.stackholders.data.model.AccountResponse
import com.ocbc.stackholders.data.model.LoggedInUser
import com.ocbc.stackholders.data.payee.PayeeListResponse
import com.ocbc.stackholders.data.payee.PaymentResponse
import com.ocbc.stackholders.data.payee.PaymentRequest
import com.ocbc.stackholders.data.transaction.TransactionHistoryResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface APIServices {
    @POST("authenticate/login")
    suspend fun getUsers(@Body loginUserRequest: LoginUserRequest) : Response<LoggedInUser>

    @GET("account/balances")
    fun getBalances() : Call<AccountResponse>

    @GET("account/payees")
    fun getPayeeList() : Call<PayeeListResponse>

    @POST("transfer")
    fun submitPayment(@Body paymentRequest: PaymentRequest) : Call<PaymentResponse>

    @GET("account/transactions")
    fun getTransactionList() : Call<TransactionHistoryResponse>
}