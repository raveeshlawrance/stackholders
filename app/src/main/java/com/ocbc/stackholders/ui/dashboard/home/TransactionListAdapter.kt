package com.ocbc.stackholders.ui.dashboard.home

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.transaction.DataItem
import com.ocbc.stackholders.networkimpl.PayeeClickListener
import com.ocbc.stackholders.util.Constants
import com.ocbc.stackholders.util.Utility
import kotlinx.android.synthetic.main.list_item_transaction.view.*
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.time.ZonedDateTime


class TransactionListAdapter(
    val activity: Context?,
    val userListResponse: List<DataItem?>?,
    val payeeClickListener: PayeeClickListener
) : RecyclerView.Adapter<TransactionListAdapter.UserListViewHolder>() {
    class UserListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        private lateinit var descriptionHint: String
        private var transferStatusHint: String = ""
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(userListResponseItem: DataItem?) {
            itemView.apply {
                val stringDate = userListResponseItem?.date
                val d: ZonedDateTime = ZonedDateTime.parse(stringDate)


                txtViewTxnDate.text = d.format(DateTimeFormatter.ofPattern(Constants.PAYMENT_DATE_FORMAT, Locale.ENGLISH))

                if(userListResponseItem?.type.equals("receive")) {
                    transferStatusHint = ""
                    descriptionHint =  "Received from: " + userListResponseItem?.from?.accountHolderName
                    txtViewTxnAmount.setTextColor(getResources().getColor(R.color.teal_200))
                } else {
                    transferStatusHint = "-"
                    descriptionHint =  "Transferring to: " + userListResponseItem?.to?.accountHolderName
                    txtViewTxnAmount.setTextColor(getResources().getColor(R.color.black))
                }
                txtViewSubDesc.text = userListResponseItem?.description ?: "--"
                txtViewTransactionDesc.text = descriptionHint
                txtViewTxnAmount.text = transferStatusHint + userListResponseItem?.currency + Utility.convertToAmount(userListResponseItem?.amount)
                //txtViewWebsite.text = userListResponseItem.website
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_transaction, parent, false)

        return UserListViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        if (userListResponse != null) {
            holder.bind(userListResponse.get(position))
        }
        holder.itemView.setOnClickListener {
            //payeeClickListener.onClickPayee(userListResponse?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return userListResponse?.let {
            userListResponse.size
        } ?: run {
            0
        }
    }
}
