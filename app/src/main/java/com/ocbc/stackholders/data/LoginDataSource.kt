package com.ocbc.stackholders.data

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.ocbc.stackholders.data.model.AccountResponse
import com.ocbc.stackholders.data.model.LoggedInUser
import com.ocbc.stackholders.networkimpl.APIServices
import com.ocbc.stackholders.networkimpl.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    suspend fun login(username: String, password: String): Result<LoggedInUser>? {
        /*var loginUserRequest = LoginUserRequest(username, password)
        val response = apiServices?.getUsers(loginUserRequest)
        if (response?.isSuccessful!!) {
            var loggedInUser : LoggedInUser = response.body()!!
            //users.postValue(response.body())
            val fakeUser = LoggedInUser("ocbc", loggedInUser.token, loggedInUser.status, loggedInUser.description, "OCBC")
            return Result.Success(fakeUser)
        } else {
            val loggedInUser : LoggedInUser = Gson().fromJson(response?.errorBody()!!.string(), LoggedInUser::class.java)
            val fakeUser = LoggedInUser("", null, loggedInUser.status, loggedInUser.description, "")
            return Result.LoginFailure(fakeUser)
            //Result.Error(IOException("Error logging in", null))
        }*/
        return null
    }

    fun getBalances(): MutableLiveData<AccountResponse> {
        val accountResponse = MutableLiveData<AccountResponse>()
        /*var apiServices = null;
        val response = apiServices?.getBalances()
        val accountResponse = MutableLiveData<AccountResponse>()

        response?.enqueue(object: Callback<AccountResponse> {
            override fun onFailure(call: Call<AccountResponse>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<AccountResponse>,
                response: Response<AccountResponse>
            ) {
                val data = response.body()
                accountResponse.value = AccountResponse(data?.status, data?.balance)
            }
        })*/

        return accountResponse
    }

    fun logout() {
        // TODO: revoke authentication
    }
}