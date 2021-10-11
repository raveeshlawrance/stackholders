package com.ocbc.stackholders.ui.dashboard.payee

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.payee.DataItem
import com.ocbc.stackholders.data.payee.PayeeListResponse
import com.ocbc.stackholders.data.payee.PayeeViewModelFactory
import com.ocbc.stackholders.databinding.ActivityLoginBinding
import com.ocbc.stackholders.databinding.FragmentPayeeBinding
import com.ocbc.stackholders.networkimpl.PayeeClickListener
import com.ocbc.stackholders.ui.BaseActivity

/**
 * A placeholder fragment containing a simple view.
 */
class SourceAccountActivity : BaseActivity<FragmentPayeeBinding>(), PayeeClickListener {

    lateinit var payeeList: PayeeListResponse
    private lateinit var payeeViewModel: PayeeViewModel
    lateinit var _binding: FragmentPayeeBinding
    var payeeClickListener : PayeeClickListener = this
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        payeeList = intent.getParcelableExtra("payee_list")!!
        payeeViewModel = ViewModelProvider(this, PayeeViewModelFactory()).get(PayeeViewModel::class.java)
        binding.rvPayeeList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvPayeeList.adapter = PayeeListAdapter(applicationContext, payeeList.data, payeeClickListener)
    }

    override fun onClickPayee(userListResponseItem: DataItem?) {
        val intent = Intent()
        intent.putExtra("keyName", userListResponseItem)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }

    override fun layoutId(): Int {
        return R.layout.fragment_payee
    }
}