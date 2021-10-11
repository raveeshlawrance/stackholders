package com.ocbc.stackholders.data.transaction

data class TransactionHistoryResponse(
	val data: List<DataItem?>? = null,
	val status: String? = null
)

data class To(
	val accountNo: String? = null,
	val accountHolderName: String? = null
)

data class From(
	val accountNo: String? = null,
	val accountHolderName: String? = null
)

data class DataItem(
	val date: String? = null,
	val amount: Double? = null,
	val description: String? = null,
	val currency: String? = null,
	val id: String? = null,
	val to: To? = null,
	val type: String? = null,
	val from: From? = null
)

