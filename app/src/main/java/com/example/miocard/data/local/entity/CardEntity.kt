package com.example.miocard.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.miocard.domain.model.Card
import java.util.Date

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey val internalId: String = java.util.UUID.randomUUID().toString(),
    val id: String,
    val prefix: String,
    val suffix: String,
    val name: String,
    val position: Int,
    val balance: Double?,
    val lastUpdate: Date?
)

fun CardEntity.toDomain(): Card {
    return Card(
        internalId = internalId,
        id = id,
        prefix = prefix,
        suffix = suffix,
        name = name,
        position = position,
        balance = balance,
        lastUpdate = lastUpdate
    )
}

fun Card.toEntity(): CardEntity {
    return CardEntity(
        internalId = internalId,
        id = id,
        prefix = prefix,
        suffix = suffix,
        name = name,
        position = position,
        balance = balance,
        lastUpdate = lastUpdate
    )
}