package com.ocbc.stackholders.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ocbc.stackholders.data.model.AccountResponse
import com.ocbc.stackholders.data.model.LoggedInUser
import com.ocbc.stackholders.data.payee.PayeeListResponse
import com.ocbc.stackholders.data.payee.PaymentRequest
import com.ocbc.stackholders.data.payee.PaymentResponse
import com.ocbc.stackholders.data.transaction.TransactionHistoryResponse
import com.ocbc.stackholders.networkimpl.APIServices
import com.ocbc.stackholders.networkimpl.NetworkServiceImpl
import javax.inject.Singleton
import com.ocbc.stackholders.ui.StackHolderApplication
import com.ocbc.stackholders.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

@Singleton
class LoginRepository @Inject constructor(apiServices: APIServices) {
    var apiServices: APIServices = apiServices
    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        //dataSource.logout()
    }

    suspend fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = loginCall(username, password)
        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    suspend fun loginCall(username: String, password: String): Result<LoggedInUser> {
        var loginUserRequest = LoginUserRequest(username, password)
        try {
            val response = apiServices.getUsers(loginUserRequest)
            if (response?.isSuccessful!!) {
                var loggedInUser : LoggedInUser = response.body()!!
                //users.postValue(response.body())
                StackHolderApplication.setAppData(Constants.TOKEN, loggedInUser.token)
                val fakeUser = LoggedInUser("ocbc", loggedInUser.token, loggedInUser.status, loggedInUser.description, "OCBC")
                return Result.Success(fakeUser)
            } else {
                val loggedInUser : LoggedInUser = Gson().fromJson(response?.errorBody()!!.string(), LoggedInUser::class.java)
                val fakeUser = LoggedInUser("", null, loggedInUser.status, loggedInUser.description, "")
                return Result.LoginFailure(fakeUser)
                //Result.Error(IOException("Error logging in", null))
            }
        } catch (ex : Exception) {
            val fakeUser = LoggedInUser("", null, "", "Server is not responding right now. Please try again later.", "")
            return Result.LoginFailure(fakeUser)
        }

    }

    fun getBalances(networkServiceImpl: NetworkServiceImpl): MutableLiveData<AccountResponse> {
        val response = apiServices.getBalances()
        val accountResponse = MutableLiveData<AccountResponse>()

        response?.enqueue(object : Callback<AccountResponse> {
            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<AccountResponse>,
                response: Response<AccountResponse>
            ) {
                val data = response.body()
                networkServiceImpl.success(response.body()!!)
                //accountResponse.value = AccountResponse(data?.status, data?.balance)
            }
        })
        return accountResponse
    }

    fun getPayeeList(networkServiceImpl: NetworkServiceImpl): MutableLiveData<PayeeListResponse> {
        val response = apiServices.getPayeeList()
        val accountResponse = MutableLiveData<PayeeListResponse>()

        response?.enqueue(object : Callback<PayeeListResponse> {
            override fun onFailure(call: Call<PayeeListResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<PayeeListResponse>,
                response: Response<PayeeListResponse>
            ) {
                val data = response.body()
                networkServiceImpl.success(response.body()!!)
                //accountResponse.value = AccountResponse(data?.status, data?.balance)
            }
        })
        return accountResponse
    }

    fun submitPayment(paymentRequest: PaymentRequest, networkServiceImpl: NetworkServiceImpl): MutableLiveData<PayeeListResponse> {
        val response = apiServices.submitPayment(paymentRequest)
        val accountResponse = MutableLiveData<PayeeListResponse>()

        response?.enqueue(object : Callback<PaymentResponse> {
            override fun onFailure(call: Call<PaymentResponse>, t: Throwable) {
                networkServiceImpl.exception(t)
            }

            override fun onResponse(
                call: Call<PaymentResponse>,
                response: Response<PaymentResponse>
            ) {
                if(response.isSuccessful) {
                    networkServiceImpl.success(response.body()!!)
                } else {
                    networkServiceImpl.error(response.errorBody().toString())
                }
            }
        })
        return accountResponse
    }

    fun getTransactionList(networkServiceImpl: NetworkServiceImpl) {
        val response = apiServices.getTransactionList()
        val transactionResponse = MutableLiveData<TransactionHistoryResponse>()

        response?.enqueue(object : Callback<TransactionHistoryResponse> {
            override fun onFailure(call: Call<TransactionHistoryResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<TransactionHistoryResponse>,
                response: Response<TransactionHistoryResponse>
            ) {
                networkServiceImpl.success(response.body()!!)
            }
        })
    }
}