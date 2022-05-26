package com.example.interview2021test1.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interview2021test1.databinding.ActivityMainBinding
import com.example.interview2021test1.model
import com.example.interview2021test1.model.CurrencyModel
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(),
    CurrencyModel.DataChangeListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCenter.start(application, "fdc79b44-659a-444a-bd98-96765ba8be19",
            Analytics::class.java, Crashes::class.java)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            model().currency.reset()
        }

        binding.list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.list.adapter = HomeScreenAdapter()

        binding.refresh.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        model().currency.addDataChangeListener(this)

        thread { th1() }
        binding.list.postDelayed({
            val thread = thread { th2() }
            binding.list.postDelayed({
                thread.interrupt()
                Log.v("SYNC_TEST", "th2.interrupt")
            }, 1000)
        }, 1000)
    }

    override fun onPause() {
        model().currency.removeDataChangeListener(this)
        super.onPause()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCurrencyDataChange() {
        binding.list.adapter?.notifyDataSetChanged()
        if (model().currency.count > 0 || model().currency.error)
            binding.refresh.isRefreshing = false
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onRefresh() {
        model().currency.reset()
    }

    val monitor = Any()
    fun th1() {
        Log.v("SYNC_TEST", "th1.in")
        synchronized(monitor) {
            Log.v("SYNC_TEST", "th1.start")
            for (i in 0 until 10000000) {
                buildString {
                    append("SYNC_TEST")
                    append(" - th1 - ")
                    append(i)
                }

                if (i % 1000 == 0)
                    Log.v("SYNC_TEST", "th1.i=$i")
            }

            Log.v("SYNC_TEST", "th1.complete")
        }
        Log.v("SYNC_TEST", "th1.out")
    }

    fun th2() {
        Log.v("SYNC_TEST", "th2.in")
        synchronized(monitor) {
            Log.v("SYNC_TEST", "th2.start")
            Log.v("SYNC_TEST", "th2.complete")
        }
        Log.v("SYNC_TEST", "th2.out")
    }

}