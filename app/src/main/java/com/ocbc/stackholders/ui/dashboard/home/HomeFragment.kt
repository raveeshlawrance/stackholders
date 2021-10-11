package com.ocbc.stackholders.ui.dashboard.home

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ocbc.stackholders.databinding.FragmentHomeBinding
import android.text.Spannable

import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocbc.stackholders.data.payee.DataItem
import com.ocbc.stackholders.networkimpl.PayeeClickListener
import com.ocbc.stackholders.ui.dashboard.payee.PayeeListAdapter
import com.ocbc.stackholders.util.Utility.convertToAmount


class HomeFragment : Fragment(), PayeeClickListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    lateinit var payeeClickListener : PayeeClickListener
    private val binding get() = _binding!!

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
        fun newInstance(): HomeFragment {
            return HomeFragment().apply {

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel =
            ViewModelProvider(this, HomeViewModelFactory()).get(HomeViewModel::class.java)
        payeeClickListener = this
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.txtViewAccntBalance
        homeViewModel._text.observe(viewLifecycleOwner, Observer {
            var currencyText = "SGD " + convertToAmount(it.balance)
            val str = SpannableStringBuilder("You have \n$currencyText\nin your account")

            str.setSpan(ForegroundColorSpan(Color.RED), 10,
                currencyText.length + 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

            textView.text = str
        })
        homeViewModel.getBalances()

        homeViewModel.getTransactionList()
        homeViewModel.transactionList.observe(viewLifecycleOwner, Observer {
            binding.rvTransactionHistory.layoutManager =
                LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.VERTICAL, false)
            binding.rvTransactionHistory.adapter = TransactionListAdapter(activity?.applicationContext, it.data, payeeClickListener)
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickPayee(userListResponseItem: DataItem?) {

    }
}