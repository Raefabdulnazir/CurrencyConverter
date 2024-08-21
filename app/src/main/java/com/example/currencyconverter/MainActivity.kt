package com.example.currencyconverter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import androidx.compose.ui.unit.dp
import androidx.activity.viewModels
import androidx.compose.foundation.focusable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.input.KeyboardType
import com.example.currencyconverter.viewmodel.CurrencyViewModel


class MainActivity : ComponentActivity() {

    private val currencyViewModel: CurrencyViewModel by viewModels()//step 1 - i am initialising the view model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverterUI(currencyViewModel)  // step 2 - i am passing the viewmodel to ui
                }
            }
        }
    }
}

@Composable
fun CurrencyConverterUI(currencyViewModel : CurrencyViewModel) {

    var amount by remember {
        mutableStateOf("")
    }
    var fromCountry by remember{
        mutableStateOf("GBP")
    }
    var toCountry by remember{
        mutableStateOf("USD")
    }

    val conversionResult  by currencyViewModel.conversionResult.observeAsState()//step 3 - observing the conversion result from viewmodel

    LaunchedEffect(conversionResult) {
        Log.d("CurrencyConverterUI","ConversionResult: $conversionResult")
    }
    
    val countries = listOf(
        "AUD - Australia",
        "CAD - Canada",
        "PLN - Poland",
        "USD - United States",
        "EUR - Eurozone",
        "JPY - Japan",
        "GBP - United Kingdom",
        "CHF - Switzerland",
        "CNY - China",
        "INR - India",
        "BRL - Brazil",
        "ZAR - South Africa",
        "MXN - Mexico",
        "KRW - South Korea",
        "SGD - Singapore",
        "NZD - New Zealand",
        "SEK - Sweden",
        "NOK - Norway",
        "DKK - Denmark",
        "RUB - Russia",
        "SAR - Saudi Arabia",
        "AED - United Arab Emirates",
        "KWD - Kuwait",
        "OMR - Oman",
        "BHD - Bahrain",
        "QAR - Qatar"
    )

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = "Currency Converter", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        CountryDropDownMenu(
            label = "From Country",
            selectedCountry = fromCountry,
            onCountrySelected = {fromCountry = it},
            countries = countries
            )

        Spacer(modifier = Modifier.height(16.dp))

        CountryDropDownMenu(
            label = "To Country",
            selectedCountry = toCountry,
            onCountrySelected = {toCountry = it},
            countries = countries
        )

        Spacer(modifier = Modifier.height(24.dp))

        TextField(
            value = amount,//binding the amount state variable
            onValueChange = {newAmount -> amount = newAmount},//updating the amount state variable with new input
            label = {Text("Enter amount")},
            modifier = Modifier.fillMaxWidth(),
            singleLine = true ,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)//this is to only show numbers in the keyboard
        )

        Spacer(modifier = Modifier.height(24.dp))

        val sendFromCountry = fromCountry.substring(0,3)
        val sendToCountry = toCountry.substring(0,3)

        Button(onClick = { //step 4 - triggering the conversion on button click
            if(amount.isNotEmpty()){
                Log.d("CurrencyConverterUI","Button Clicked , calling convertCurrency")
                currencyViewModel.convertCurrency(sendFromCountry,sendToCountry,amount.toDouble())//telling the viewmodel to perform conversion
            }
        }) {
            Text("CONVERT")
        }

        Spacer(modifier = Modifier.height(24.dp))

        //step 5 - displaying the converted amount
        //val convertedAmount = conversionResult?.rates?.get(toCountry)
        TextField(
            value = conversionResult?.toString() ?:"",
            onValueChange = {},
            enabled = false,
            label = {Text("Converted amount")} ,
            readOnly = true ,
            //modifier = Modifier.fillMaxWidth()
            modifier = Modifier.clickable{}.focusable(false)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryDropDownMenu(
    label : String,
    selectedCountry : String,
    onCountrySelected : (String) -> Unit,
    countries : List<String>
) {
    var expanded by remember{ mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded})
    {
        TextField(
            value = selectedCountry,
            onValueChange = {},
            label = {Text(label)},
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),//menuAnchor resolved the menu dropdown not coming issue
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            }, readOnly = true  //make sure that user cannot type directly

        )
        //this is the actual dropdown menu , that shows the list of countries
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
            )
        {
            //list of items in this dropdown menu
            countries.forEach { country  ->
                DropdownMenuItem(
                    text = { Text(text = country) },
                    onClick = {
                        onCountrySelected(country)
                         expanded = false   //close dropdown after selection
                    }
                )
            }
        }

    }
}
    


/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CurrencyConverterTheme {
        CurrencyConverterUI()
    }
}*/
