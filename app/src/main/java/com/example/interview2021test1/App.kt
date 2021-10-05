package com.example.interview2021test1

import android.app.Application
import android.widget.Toast
import com.example.interview2021test1.api.CoinMarketCapApi
import com.example.interview2021test1.model.AppModelRoot
import com.example.interview2021test1.toolkit.ThreadPool

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    internal lateinit var api: CoinMarketCapApi
    internal lateinit var model: AppModelRoot

    override fun onCreate() {
        super.onCreate()

        model = AppModelRoot()
        api = CoinMarketCapApi()
        instance = this
    }

    fun showMessage() {
        ThreadPool.UI.post {
            Toast.makeText(this, R.string.error_default, Toast.LENGTH_SHORT).show()
        }
    }
}

fun app() = App.instance
fun model() = App.instance.model
fun api() = App.instance.api
