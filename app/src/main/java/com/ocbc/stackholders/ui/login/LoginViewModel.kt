package com.ocbc.stackholders.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns
import com.ocbc.stackholders.data.LoginRepository
import com.ocbc.stackholders.data.Result

import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.BaseViewModel
import com.ocbc.stackholders.data.LoginUserRequest
import com.ocbc.stackholders.data.model.LoggedInUser
import com.ocbc.stackholders.networkimpl.APIServices
import javax.inject.Inject

class LoginViewModel : BaseViewModel() {
    @Inject
    lateinit var loginRepository : LoginRepository

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginResult = MutableLiveData<Result<LoggedInUser>>()
    val loginResult: LiveData<Result<LoggedInUser>> = _loginResult

    suspend fun login(username: String, password: String) {
        _loginResult.value = loginRepository.login(username, password)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }
}