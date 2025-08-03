package com.example.miocard.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.miocard.domain.model.Card
import java.util.Date

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val id: String,
    val prefix: String,
    val suffix: String,
    val name: String,
    val balance: Double?,
    val lastUpdate: Date?
)

fun CardEntity.toDomain(): Card {
    return Card(
        id = id,
        prefix = prefix,
        suffix = suffix,
        name = name,
        balance = balance,
        lastUpdate = lastUpdate
    )
}

fun Card.toEntity(): CardEntity {
    return CardEntity(
        id = id,
        prefix = prefix,
        suffix = suffix,
        name = name,
        balance = balance,
        lastUpdate = lastUpdate
    )
}