package com.example.interview2021test1.api.model

class ApiCurrencyResponse {
    val status = ApiResponseStatus()
    lateinit var data: Array<ApiCurrency>
}

