package com.ocbc.stackholders.data.payee

data class PaymentRequest(
	val date: String? = null,
	val amount: Double? = null,
	val recipientAccountNo: String? = null,
	val description: String? = null
)

