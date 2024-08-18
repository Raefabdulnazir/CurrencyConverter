package com.example.currencyconverter.network

import android.icu.util.CurrencyAmount
import com.example.currencyconverter.model.CurrencyResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApiService{
    @GET("convert") //endpoint
    suspend fun convertCurrency(
        @Query("access_key") accesskey: String,
        @Query("from") fromCurrency: String,
        @Query("to") toCurrency: String,
        @Query("amount") amount: Double
    ): Response<CurrencyResponse>
}