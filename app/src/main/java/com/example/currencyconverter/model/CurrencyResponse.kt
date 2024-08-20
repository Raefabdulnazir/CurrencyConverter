package com.example.currencyconverter.model

import com.google.gson.annotations.SerializedName
//@SerializedName in gson library is used to map JSON field names to Kotlin variables
data class ExchangeRateResponse(
    val status_code: Int,
    val data: ExchangeRateData
)

data class ExchangeRateData(
    val base: String,
    val target: String,
    val mid: Double,
    val unit: Int,
    val timestamp: String
)
/*
the data json response will be
{
    "success": true,
    "timestamp": 1628872746,
    "base": "GBP",
    "date": "2024-08-19",
    "rates": {
    "USD": 1.3872,
    "AUD": 1.8851
}
}*/
