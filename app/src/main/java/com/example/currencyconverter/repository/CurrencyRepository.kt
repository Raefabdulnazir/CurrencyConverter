package com.example.currencyconverter.repository

import android.util.Log
import com.example.currencyconverter.api.RetrofitInstance
import com.example.currencyconverter.model.ExchangeRateResponse
import com.example.currencyconverter.network.CurrencyApiService
import retrofit2.Call
import retrofit2.Response
import java.time.temporal.TemporalAmount
import java.util.Currency

class CurrencyRepository {
    private val apiService: CurrencyApiService = RetrofitInstance.api

    suspend fun getExchangeRates(
        base: String,
        target: String,
    ): Response<ExchangeRateResponse> {
        Log.d("CurrencyRepository", "Requesting conversion: base=$base, symbols=$target")
        val response = apiService.convertCurrency(
            //accesskey = "e31e18dde001870978e77a622d987bff",
            base = base,
            target = target
            )
        Log.d("CurrencyRepository","API response: ${response.body()}")
        return response
    }
}