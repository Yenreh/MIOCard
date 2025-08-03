package com.example.miocard.domain.model

import java.util.Date

data class CardBalance(
    val balance: Double,
    val cardNumber: String,
    val balanceDate: Date
)