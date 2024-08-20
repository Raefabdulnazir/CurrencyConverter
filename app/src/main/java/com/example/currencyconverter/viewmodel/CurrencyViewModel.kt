package com.example.currencyconverter.viewmodel

import android.icu.util.CurrencyAmount
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.model.ExchangeRateResponse
import com.example.currencyconverter.repository.CurrencyRepository
import kotlinx.coroutines.launch
import retrofit2.Response

import java.util.Currency

class CurrencyViewModel : ViewModel(){
    private val _exchangeRates = MutableLiveData<Map<String,Double>>()
    val exchangeRates: LiveData<Map<String,Double>> get() = _exchangeRates

    private val _conversionResult = MutableLiveData<Double>()
    val conversionResult: LiveData<Double> get() = _conversionResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val repository: CurrencyRepository = CurrencyRepository()

/*    fun fetchExchangeRates(baseCurrency: String,symbols: String){
        viewModelScope.launch {
            try{
                val response = repository.getExchangeRates(baseCurrency,symbols)
                if(response.isSuccessful){
                    response.body()?.let{
                        if(it.status_code == 200){
                            _exchangeRates.value = mapOf(it.data.target to it.data.mid)
                        }else{
                            _error.value = "Failed to fetch response"
                        }
                    } ?: run {
                        _error.value = "Empty response"
                    }
                } else{
                    _error.value = "API Error: ${response.message()}"
                }
            }
            catch (e:Exception){
                _error.value = "Network Error: ${e.message}"
            }
        }
    }*/

    fun convertCurrency(fromCurrency: String,toCurrency: String,amount: Double){
        viewModelScope.launch{
            try{
                Log.d("CurrencyViewModel", "Calling convertCurrency API")
                val response = repository.getExchangeRates(fromCurrency,toCurrency)
                if(response.isSuccessful){
                    Log.d("convertCurrency","API call successfull : ${response.body()}")
                    response.body()?.let {
                        if(it.status_code == 200){
                            val rate = it.data.mid
                            _conversionResult.value = (amount * (rate ?: 0.0))
                        }else{
                            _error.value = "Failed to fetch conversion rate"
                        }
                    } ?:{
                        _error.value = "Empty Response"
                    }
                }else{
                    _error.value = "API Error : ${response.message()}"
                }
            }
            catch (e:Exception){
                _error.value = "Network Error : ${e.message}"
            }
        }
    }

}