package com.ocbc.stackholders.ui

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.ocbc.stackholders.ui.dashboard.DashboardActivity
import java.time.Month
import java.util.*

abstract class BaseFragment<DB : ViewDataBinding> : Fragment() {
    lateinit var baseActivity: BaseActivity<DB>
    lateinit var mProgressDialog: ProgressDialog
    private var _binding: DB? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mProgressDialog = ProgressDialog(activity)
        _binding = getViewBinding()
        baseActivity = activity as BaseActivity<DB>
        return binding.root
    }
    abstract fun getViewBinding(): DB

    fun setScreenTitle(resId: Int) {

    }

    fun setScreenTitle(title: String) {

    }

    fun getBackButton(): ImageButton? {
        return null
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
        val builder = AlertDialog.Builder(activity?.applicationContext!!)
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
        val builder = AlertDialog.Builder(baseActivity)
        builder.setTitle(title)
        builder.setMessage(body)
        builder.setPositiveButton(android.R.string.yes) { dialog, which -> }
        builder.setNegativeButton(android.R.string.no) { dialog, which -> }
        builder.show()
    }

    fun showDatePicker(datePickerListener: DatePickerListener, context: DashboardActivity) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(baseActivity, { view, year, monthOfYear, dayOfMonth ->

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePickerListener.onPickDate("$dayOfMonth ${Month.of(monthOfYear + 1).name.substring(0, 3)}, $year")
            }

        }, year, month, day)
        dpd.datePicker.minDate = System.currentTimeMillis()

        dpd.show()

    }

    interface AlertClickListener {
        fun onPositiveClickListener()
        fun onNegativeClickListener()
    }

    interface DatePickerListener {
        fun onPickDate(selected : String)
    }

    @LayoutRes
    abstract fun layoutId() : Int
}