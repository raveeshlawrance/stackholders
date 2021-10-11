package com.ocbc.stackholders.ui

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.time.Month
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {
    private lateinit var baseActivity: BaseActivity<DB>
    lateinit var mTextViewScreenTitle: TextView
    lateinit var mImageButtonBack: ImageButton
    lateinit var mProgressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(this.layoutId())
        baseActivity = this
        mProgressDialog = ProgressDialog(this)
    }

    val binding by lazy {
        DataBindingUtil.setContentView(this, layoutId()) as DB
    }

    fun setScreenTitle(resId: Int) {
        mTextViewScreenTitle.text = getString(resId)
    }

    fun setScreenTitle(title: String) {
        mTextViewScreenTitle.text = title
    }

    fun getBackButton(): ImageButton {
        return mImageButtonBack;
    }

    fun showProgressDialog(message : String) {
        mProgressDialog.setMessage(message ?: "Loading..")
        mProgressDialog.setCancelable(false)
        mProgressDialog.isIndeterminate = true
        if(!mProgressDialog.isShowing) {
            mProgressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }
    fun showAlert(title : String, body : String, alertClickListener: AlertClickListener?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            alertClickListener?.onPositiveClickListener()
        }

        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            alertClickListener?.onNegativeClickListener()
        }
        builder.show()


    }
    fun showAlert(title: String, body: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton(android.R.string.yes) { dialog, which -> }
        builder.setNegativeButton(android.R.string.no) { dialog, which -> }
        builder.show()
    }

    fun showDatePicker(datePickerListener : DatePickerListener) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(baseActivity, { view, year, monthOfYear, dayOfMonth ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePickerListener.onPickDate("$dayOfMonth ${Month.of(monthOfYear + 1).name.substring(0, 3)}, $year")
            }

        }, year, month, day)

        dpd.show()

    }

    interface AlertClickListener {
        fun onPositiveClickListener()
        fun onNegativeClickListener()
    }

    interface DatePickerListener {
        fun onPickDate(selected : String)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkNetworkConnection(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager?.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    @LayoutRes
    abstract fun layoutId() : Int
}