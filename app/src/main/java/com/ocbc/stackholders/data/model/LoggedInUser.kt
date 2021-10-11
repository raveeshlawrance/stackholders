package com.ocbc.stackholders.data.model

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
    val userId: String,
    val token: String?,
    val status : String,
    val description : String?,
    val displayName: String
)