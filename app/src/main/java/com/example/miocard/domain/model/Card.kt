package com.example.miocard.domain.model

import java.util.Date

data class Card(
    val internalId: String = java.util.UUID.randomUUID().toString(),
    val id: String,
    val prefix: String = "",
    val suffix: String = "",
    val name: String,
    val position: Int = 0,
    val balance: Double? = null,
    val lastUpdate: Date? = null
) {
    val displayId: String
        get() = buildString {
            if (prefix.isNotEmpty()) append("$prefix ")
            append(id)
            if (suffix.isNotEmpty()) append(" $suffix")
        }
}