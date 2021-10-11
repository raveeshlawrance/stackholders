package com.ocbc.stackholders.networkimpl

import java.lang.Exception

interface ErrorHandler {
    fun handleError(ex : Exception) : Throwable
}