package com.ocbc.stackholders.data.payee

/**
 * Authentication result : success (user details) or error message.
 */
data class PaymentStatusResult(
        val success: PaymentResponse? = null,
        val error: String? = null
)