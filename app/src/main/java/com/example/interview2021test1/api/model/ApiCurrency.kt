package com.example.interview2021test1.api.model

class ApiCurrency {

    var id = 0L
    var name = ""
    var symbol = ""
    val quote = ApiCurrencyQuote()
}

class ApiCurrencyQuote {
    val USD = ApiCurrencyQuoteUsd()
}

class ApiCurrencyQuoteUsd {
    var price = 0.0
    var percent_change_1h = 0.0
}