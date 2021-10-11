package com.ocbc.stackholders.ui.dashboard.payee

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocbc.stackholders.R
import com.ocbc.stackholders.data.payee.DataItem
import com.ocbc.stackholders.networkimpl.PayeeClickListener
import kotlinx.android.synthetic.main.list_item_payee.view.*

class PayeeListAdapter(
    val activity: Context?,
    val userListResponse: List<DataItem?>?,
    val payeeClickListener: PayeeClickListener
) : RecyclerView.Adapter<PayeeListAdapter.UserListViewHolder>() {

    class UserListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind(userListResponseItem: DataItem?) {
            itemView.apply {
                txtViewAccntNo.text = userListResponseItem?.accountNo
                txtViewAccountName.text = userListResponseItem?.accountHolderName
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_payee, parent, false)

        return UserListViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserListViewHolder, position: Int) {
        if (userListResponse != null) {
            holder.bind(userListResponse.get(position))
        }
        holder.itemView.setOnClickListener {
            payeeClickListener.onClickPayee(userListResponse?.get(position))
        }
    }

    override fun getItemCount(): Int {
        return userListResponse?.let {
            userListResponse.size
        } ?: run {
            0
        }
    }

    /*fun refreshUserList(userList: List<DataItem?>) {
        this.userListResponse.apply {
            clear()
            addAll(userList)
            notifyDataSetChanged()
        }
    }*/
}
