package com.example.composecrypto.service

import com.example.composecrypto.model.Crypto
import com.example.composecrypto.model.CryptoDetail
import com.example.composecrypto.model.CryptoDetailItem
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {

    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key: String
    ): Crypto

    @GET("currencies")
    suspend fun getCryptoDetail(
        @Query("key") key: String,
        @Query("ids") id: String,
        @Query("attributes") attributes: String
    ): CryptoDetail
}