package com.ocbc.stackholders.util

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

object Utility {
    fun covertDateToServerDate(date: String?) : String {
        return try {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZ")
            val netDate = Date(date)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun getCurrentDate(milliSeconds: Long, dateFormat: String?): String? {
        val formatter = SimpleDateFormat(dateFormat)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
    }

    fun covertToUIDateFormat(selectedDate: String?, dateFormat: String?): String? {
        return try {
            val sdf = SimpleDateFormat(dateFormat)
            val netDate = Date(selectedDate)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    fun convertToAmount(amount: Double?): String? {
        val formatter: NumberFormat = DecimalFormat("#,###.##")
        return formatter.format(amount)
        /*val formatter = DecimalFormat("#,###.####")
        return formatter.format(amount)*/
    }

}