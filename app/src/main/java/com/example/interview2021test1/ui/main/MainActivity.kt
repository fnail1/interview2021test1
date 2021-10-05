package com.example.interview2021test1.ui.main

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.interview2021test1.databinding.ActivityMainBinding
import com.example.interview2021test1.model
import com.example.interview2021test1.model.CurrencyModel

class MainActivity : AppCompatActivity(),
    CurrencyModel.DataChangeListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

}