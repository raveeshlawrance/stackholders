package com.ocbc.stackholders.storage

import android.content.SharedPreferences
import com.ocbc.stackholders.util.Constants
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StackHolderKeystore @Inject constructor(preferences: SharedPreferences) {
    val mSharedPreferences: SharedPreferences = preferences

    fun setLoginToken(token: String?) {
        mSharedPreferences.edit().putString(Constants.TOKEN, token).apply()
    }

    fun getLoginToken() : String? {
        return mSharedPreferences.getString(Constants.TOKEN, "")
    }
}