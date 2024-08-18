package com.example.currencyconverter.model

import com.google.gson.annotations.SerializedName
//@SerializedName in gson library is used to map JSON field names to Kotlin variables
data class CurrencyResponse(
    @SerializedName("success") val success : Boolean,
    @SerializedName("timestamp") val timestamp : Long,
    @SerializedName("base") val base : String,
    @SerializedName("date") val date : String,
    @SerializedName("rates") val rates : Map<String,Double> = emptyMap()
)