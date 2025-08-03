package com.example.miocard.domain.repository

import com.example.miocard.domain.model.Card
import com.example.miocard.domain.model.CardBalance
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    fun getAllCards(): Flow<List<Card>>
    suspend fun insertCard(card: Card)
    suspend fun updateCard(card: Card)
    suspend fun deleteCard(card: Card)
    suspend fun getCardById(id: String): Card?
    suspend fun getCardBalance(card: Card): Result<CardBalance>
}