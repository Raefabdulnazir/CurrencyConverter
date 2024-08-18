package com.example.currencyconverter.repository

import com.example.currencyconverter.api.RetrofitInstance
import com.example.currencyconverter.model.CurrencyResponse
import com.example.currencyconverter.network.CurrencyApiService
import retrofit2.Call
import retrofit2.Response
import java.time.temporal.TemporalAmount
import java.util.Currency

class CurrencyRepository {
    private val apiService: CurrencyApiService = RetrofitInstance.api

    suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Response<CurrencyResponse> {
        return apiService.convertCurrency(
            accesskey = "e31e18dde001870978e77a622d987bff",
            fromCurrency = fromCurrency,
            toCurrency = toCurrency,
            amount = amount
        )
    }
}