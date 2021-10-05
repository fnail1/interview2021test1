package com.example.interview2021test1.model.types

class Currency {
    var id: Long = 0L
    lateinit var name: String
    lateinit var symbol: String
    var price: Double = 0.0
    var percentChange1h: Double = 0.0
}