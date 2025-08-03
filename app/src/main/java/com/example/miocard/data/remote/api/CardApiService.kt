package com.example.miocard.data.remote.api

import com.example.miocard.data.remote.dto.CardBalanceResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CardApiService {
    @GET("https://www.utryt.com.co/saldo/script/saldo.php")
    suspend fun getCardBalance(
        @retrofit2.http.Query("card") card: String
    ): Response<CardBalanceResponse>
}