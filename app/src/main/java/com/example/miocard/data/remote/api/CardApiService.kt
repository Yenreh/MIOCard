package com.example.miocard.data.remote.api

import com.example.miocard.data.remote.dto.CardBalanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApiService {
    @GET("cards/{cardId}/balance")
    suspend fun getCardBalance(@Path("cardId") cardId: String): Response<CardBalanceResponse>
}