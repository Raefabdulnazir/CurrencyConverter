# Currency Converter App Documentation

## Project Overview
The Currency Converter app is an Android application that allows users to convert one currency to another using real-time exchange rates fetched from an API.

## Key Components Used

### 1. **Retrofit**
- **Purpose**: Retrofit is used to handle HTTP requests and communicate with the currency conversion API.
- **Setup**: 
  - The `RetrofitInstance` object provides a singleton instance of the `CurrencyApiService` interface, ensuring that the network layer is efficient and consistent.
  - **BASE_URL**: `https://hexarate.paikama.co/api/`

### 2. **Data Models**
- **ExchangeRateResponse**: This data class models the API's JSON response structure. 
  - `status_code`: The status code returned by the API.
  - `data`: An instance of `ExchangeRateData`, which contains the actual exchange rate data.

- **ExchangeRateData**: This data class encapsulates the details of the exchange rate.
  - `base`: The base currency code.
  - `target`: The target currency code.
  - `mid`: The mid-market exchange rate.
  - `unit`: Unit multiplier for the exchange rate.
  - `timestamp`: Timestamp when the exchange rate was retrieved.

### 3. **API Interface**
- **CurrencyApiService**: Defines the endpoints for the currency conversion service.
  - **convertCurrency**: A `suspend` function that takes the `base` currency and `target` currency as parameters and returns the `ExchangeRateResponse` wrapped in a `Response` object.
  - **Endpoint**: `rates/latest/{base}`

### 4. **Repository Pattern**
- **CurrencyRepository**: Acts as a data provider for the app by interacting with the `CurrencyApiService`. 
  - It fetches exchange rates by calling the `convertCurrency` method from `CurrencyApiService`.
  - Logs are generated to track the API requests and responses.

### 5. **UI Components**
- **TextField**: Used to take user input for the amount to convert. It restricts input to digits only and displays a warning if other characters are entered.
  - **Code**: `keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)`
- **Dropdown Menus**: Used for selecting the base and target currencies.

### 6. **Handling API Response**
- The app handles API responses using Retrofit's `Response` object, ensuring proper error handling and logging for debugging purposes.

## How It Works
1. **User Input**: The user enters the amount and selects the base and target currencies.
2. **API Call**: Upon pressing the convert button, the app makes an API call using Retrofit to fetch the latest exchange rate between the selected currencies.
3. **Display Result**: The app displays the converted amount based on the fetched exchange rate.

## Conclusion
This project integrates various key Android development concepts, including network communication, data modeling, and UI development using Jetpack Compose. By documenting the components and their interactions, this document serves as a reference for revisiting and extending the app in the future.
