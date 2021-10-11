package com.ocbc.stackholders.ui.login

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.ocbc.stackholders.databinding.ActivityLoginBinding

import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.model.LoggedInUser
import com.ocbc.stackholders.storage.StackHolderKeystore
import com.ocbc.stackholders.ui.BaseActivity
import com.ocbc.stackholders.ui.StackHolderApplication
import com.ocbc.stackholders.ui.dashboard.DashboardActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var stackHolderKeystore: StackHolderKeystore
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = binding.username
        val password = binding.password
        val login = binding.login
        //val loading = binding.loading

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both username / password is valid
            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            //loading.visibility = View.GONE
            if (loginResult is com.ocbc.stackholders.data.Result.Success) {
                updateUiWithUser(loginResult.data)
            } else if(loginResult is com.ocbc.stackholders.data.Result.LoginFailure) {
                showAlert(getString(R.string.login_failed), loginResult.data.description!!)
            } else {
                showAlert(getString(R.string.login_failed), getString(R.string.default_error_msg))
            }
        })

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(),
                password.text.toString()
            )
        }

        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        callLoginApi(username.text.toString(), password.text.toString())
                }
                false
            }

            login.setOnClickListener {
                //loading.visibility = View.VISIBLE
                callLoginApi(username.text.toString(), password.text.toString())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun callLoginApi(username : String, password : String) {
        var isNetworkAvl : Boolean = checkNetworkConnection()
        if(!isNetworkAvl) {
            showAlert("Network Error", "Please check your network connection and try agin later")
        } else if(username.equals("") || password.equals("")) {
            showAlert("Login failed", "Please enter Username/Password")
        } else {
            GlobalScope.launch (Dispatchers.Main) {
                loginViewModel.login(username, password)
            }
        }

    }



    private fun updateUiWithUser(loggedInUser: LoggedInUser) {
        var stockAppl : StackHolderApplication = applicationContext as StackHolderApplication
        stockAppl.stackHolderKeystore?.setLoginToken(loggedInUser.token)
        var dashboardActivity = Intent(this, DashboardActivity::class.java)
        startActivity(dashboardActivity)
        finish()
    }

    override fun layoutId(): Int {
        return R.layout.activity_login
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