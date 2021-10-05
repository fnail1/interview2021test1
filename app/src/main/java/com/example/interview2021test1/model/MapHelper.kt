package com.example.interview2021test1.model

import com.example.interview2021test1.api.model.ApiCurrency
import com.example.interview2021test1.model.types.Currency

object MapHelper {
    fun toCurrency(apiCurrency: ApiCurrency) = Currency().apply {
        id = apiCurrency.id
        name = apiCurrency.name
        symbol = apiCurrency.symbol
        price = apiCurrency.quote.USD.price
        percentChange1h = apiCurrency.quote.USD.percent_change_1h
    }
}
