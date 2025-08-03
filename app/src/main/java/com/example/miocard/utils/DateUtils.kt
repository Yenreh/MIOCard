package com.example.miocard.utils

import android.content.Context
import java.text.DateFormat
import java.util.*

object DateUtils {
    
    fun formatDate(context: Context, date: Date): String {
        val locale = context.resources.configuration.locale
        
        val dateFormat = DateFormat.getDateTimeInstance(
            DateFormat.LONG,
            DateFormat.SHORT,
            locale
        )
        
        return dateFormat.format(date)
    }
    
    fun formatDateWithLocale(date: Date, locale: Locale): String {
        val calendar = Calendar.getInstance().apply { time = date }
        
        return when (locale.language) {
            "es" -> {
                // Spanish format: "2 de enero de 2024, 10:30 AM"
                val options = mapOf(
                    "day" to "numeric",
                    "month" to "long", 
                    "year" to "numeric",
                    "hour" to "numeric",
                    "minute" to "2-digit",
                    "hour12" to true
                )
                formatWithOptions(date, locale, options)
            }
            else -> {
                // English format: "January 2, 2024, 10:30 AM"
                val options = mapOf(
                    "day" to "numeric",
                    "month" to "long", 
                    "year" to "numeric",
                    "hour" to "numeric",
                    "minute" to "2-digit",
                    "hour12" to true
                )
                formatWithOptions(date, locale, options)
            }
        }
    }
    
    private fun formatWithOptions(date: Date, locale: Locale, options: Map<String, Any>): String {
        // Simplified implementation - in a real app, use proper date formatting libraries
        val dateFormat = DateFormat.getDateTimeInstance(
            DateFormat.LONG,
            DateFormat.SHORT,
            locale
        )
        return dateFormat.format(date)
    }
}