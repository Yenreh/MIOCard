package com.example.miocard.domain.usecase

import com.example.miocard.domain.model.Card
import com.example.miocard.domain.repository.CardRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(
    private val cardRepository: CardRepository
) {
    operator fun invoke(): Flow<List<Card>> {
        return cardRepository.getAllCards()
    }
}