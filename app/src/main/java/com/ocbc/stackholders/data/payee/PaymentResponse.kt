package com.ocbc.stackholders.data.payee

data class PaymentResponse(
	val data: Data? = null,
	val status: String? = null
)

data class Data(
	val date: String? = null,
	val amount: Double? = null,
	val recipientAccountNo: String? = null,
	val description: String? = null,
	val id: String? = null
)

