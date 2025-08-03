package com.example.miocard.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.example.miocard.domain.model.CardBalance
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class CardBalanceResponse(
    @SerializedName("balance")
    val balance: Double,
    @SerializedName("cardNumber")
    val cardNumber: String,
    @SerializedName("balanceDate")
    val balanceDate: String // Assuming it comes as ISO string
)

fun CardBalanceResponse.toDomain(): CardBalance {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    val date = try {
        dateFormat.parse(balanceDate) ?: Date()
    } catch (e: Exception) {
        Date()
    }
    
    return CardBalance(
        balance = balance,
        cardNumber = cardNumber,
        balanceDate = date
    )
}