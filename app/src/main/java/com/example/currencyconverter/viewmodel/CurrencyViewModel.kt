package com.example.currencyconverter.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.model.CurrencyResponse
import com.example.currencyconverter.repository.CurrencyRepository
import kotlinx.coroutines.launch
import retrofit2.Response

import java.util.Currency

class CurrencyViewModel : ViewModel(){

    private val repository = CurrencyRepository()

    private val _conversionResult = MutableLiveData<CurrencyResponse?>()
    val conversionResult : LiveData<CurrencyResponse?> = _conversionResult

    fun convertCurrency(fromCurrency: String, toCurrency: String, amount: Double){
        viewModelScope.launch {
            try{
                val response = repository.convertCurrency(fromCurrency,toCurrency,amount)
                Log.d("CurrencyViewModel", "Response: ${response.body()}")
                if(response.isSuccessful)
                    _conversionResult.postValue(response.body())
                else{
                    Log.e("CurrencyViewModel","Error: ${response.errorBody()?.string()}")
                    _conversionResult.postValue(null)
                }
            }
            catch (e: Exception){
                Log.e("CurrencyViewModel","Exception: ${e.message}",e)
                _conversionResult.postValue(null)
            }
            val response = repository.convertCurrency(fromCurrency,toCurrency,amount)
            _conversionResult.postValue(null)
        }
    }
}