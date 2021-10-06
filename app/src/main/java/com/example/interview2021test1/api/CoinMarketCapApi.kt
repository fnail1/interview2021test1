package com.example.interview2021test1.api

import android.util.Log
import com.example.interview2021test1.BuildConfig
import com.example.interview2021test1.api.model.ApiCurrencyResponse
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger

class CoinMarketCapApi {
    private val gson = Gson()

    fun getCurrencies(skip: Int, limit: Int): ApiCurrencyResponse {
        val uri =
            "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?start=${skip + 1}&limit=$limit"

        Log.v("API", uri)

        val connection = URL(uri).openConnection() as HttpURLConnection
        connection.requestMethod = "GET"
        connection.doInput = true
        connection.doOutput = false
        connection.addRequestProperty("X-CMC_PRO_API_KEY", "65987cb0-deaa-4598-a7c2-1526be6771eb")
        connection.addRequestProperty("X-Accept", "application/json")
        connection.addRequestProperty("Connection", "Keep-Alive")

        try {
            when (val responseCode = connection.responseCode) {
                HttpURLConnection.HTTP_OK -> {
                    return if (BuildConfig.DEBUG) {
                        val json = connection.inputStream.reader().use {
                            it.readText()
                        }

                        Log.v("API", json)

                        gson.fromJson(json, ApiCurrencyResponse::class.java)
                    } else {
                        connection.inputStream.reader().use {
                            gson.fromJson(it, ApiCurrencyResponse::class.java)
                        }
                    }
                }
                else -> throw Exception("$responseCode ${connection.responseMessage}")
            }
        } finally {
            connection.disconnect()
        }
    }
}