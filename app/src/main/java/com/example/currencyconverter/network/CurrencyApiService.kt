package com.example.currencyconverter.network

import android.icu.util.CurrencyAmount
import com.example.currencyconverter.model.ExchangeRateResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
//this is my api call

//this is then url structure - URL: https://hexarate.paikama.co/api/rates/latest/AUD?target=INR
interface CurrencyApiService{
    @GET("rates/latest/{base}") //endpoint
    suspend fun convertCurrency(
        //@Query("access_key") accesskey: String,
        @Path("base") base: String,
        @Query("target") target: String
    ): Response<ExchangeRateResponse>
}