package com.example.miocard.data.repository

import com.example.miocard.data.local.dao.CardDao
import com.example.miocard.data.local.entity.toDomain
import com.example.miocard.data.local.entity.toEntity
import com.example.miocard.data.remote.api.CardApiService
import com.example.miocard.data.remote.dto.toDomain
import com.example.miocard.domain.model.Card
import com.example.miocard.domain.model.CardBalance
import com.example.miocard.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao,
    private val cardApiService: CardApiService
) : CardRepository {

    override fun getAllCards(): Flow<List<Card>> {
        return cardDao.getAllCards().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun insertCard(card: Card) {
        cardDao.insertCard(card.toEntity())
    }

    override suspend fun updateCard(card: Card) {
        cardDao.updateCard(card.toEntity())
    }

    override suspend fun deleteCard(card: Card) {
        cardDao.deleteCard(card.toEntity())
    }

    override suspend fun getCardById(id: String): Card? {
        return cardDao.getCardById(id)?.toDomain()
    }

    override suspend fun getCardBalance(card: Card): Result<CardBalance> {
        return try {
            val cardParam = card.prefix + card.id + card.suffix
            val response = cardApiService.getCardBalance(cardParam)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!.toDomain())
            } else {
                Result.failure(Exception("Failed to fetch balance: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}