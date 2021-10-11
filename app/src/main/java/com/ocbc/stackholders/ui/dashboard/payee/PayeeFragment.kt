package com.ocbc.stackholders.ui.dashboard.payee

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.payee.DataItem
import com.ocbc.stackholders.data.payee.PayeeViewModelFactory
import com.ocbc.stackholders.databinding.FragmentPayeeBinding
import com.ocbc.stackholders.networkimpl.PayeeClickListener
import com.ocbc.stackholders.ui.BaseFragment
import com.ocbc.stackholders.ui.dashboard.transfer.TransferActivity

/**
 * A placeholder fragment containing a simple view.
 */
class PayeeFragment : BaseFragment<FragmentPayeeBinding>(), PayeeClickListener {

    private lateinit var payeeViewModel: PayeeViewModel
    var payeeClickListener : PayeeClickListener = this
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        payeeViewModel = ViewModelProvider(this, PayeeViewModelFactory()).get(PayeeViewModel::class.java)
        payeeViewModel.getPayeeList()
        payeeViewModel._text.observe(viewLifecycleOwner, Observer {
            binding.rvPayeeList.layoutManager =
                LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
            binding.rvPayeeList.adapter = PayeeListAdapter(activity?.applicationContext, it.data, payeeClickListener)
        })

        return binding.root
    }

    override fun onClickPayee(userListResponseItem: DataItem?) {
        val intent = Intent(activity, TransferActivity::class.java)
        intent.putExtra("keyName", userListResponseItem)
        startActivity(intent)
        //setResult(Activity.RESULT_OK, intent)
        //finish()
        /*childFragmentManager.beginTransaction()
            .replace(R.id.root_container, TransferFragment.newInstance(1))
            .commitAllowingStateLoss()*/
    }

    override fun layoutId(): Int {
        return R.layout.fragment_payee
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PayeeFragment {
            return PayeeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun getViewBinding(): FragmentPayeeBinding {
        return FragmentPayeeBinding.inflate(layoutInflater)
    }
}