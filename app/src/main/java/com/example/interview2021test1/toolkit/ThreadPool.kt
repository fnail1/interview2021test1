package com.example.interview2021test1.toolkit

import android.os.Handler
import android.os.Looper
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object ThreadPool {
    val UI = Handler(Looper.getMainLooper())

    private val threads = ThreadPoolExecutor(4, 4, 1, TimeUnit.MINUTES, LinkedBlockingQueue())

    fun execute(task: Runnable) = threads.execute(task)
}