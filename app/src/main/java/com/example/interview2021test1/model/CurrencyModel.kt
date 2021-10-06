package com.example.interview2021test1.model

import androidx.annotation.UiThread
import com.example.interview2021test1.R
import com.example.interview2021test1.api
import com.example.interview2021test1.app
import com.example.interview2021test1.model.types.Currency
import com.example.interview2021test1.toolkit.ThreadPool
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class CurrencyModel {
    companion object {
        private const val PAGE_SIZE: Int = 30
    }

    private var data = ArrayList<Currency>()
    private var offfset = 0

    val count: Int
        @UiThread
        get() = data.size

    @UiThread
    fun getItem(i: Int) =
        data[i]


    var eofReached: Boolean = false
        @UiThread
        get
        private set

    var error = false
        @UiThread
        get
        private set


    @UiThread
    fun reset() {
        data = ArrayList()
        offfset = 0
        error = false
        onDataChange()
        requestNextPage()
    }

    @UiThread
    fun requestNextPage() {
        ThreadPool.execute {
            try {
                val response = api().getCurrencies(offfset, PAGE_SIZE)
                val newData = response.data.map { MapHelper.toCurrency(it) }
                offfset += PAGE_SIZE

                ThreadPool.UI.post {
                    data += newData
                    eofReached = data.size >= response.status.total_count
                    onDataChange()
                }
            } catch (e: IOException) {
                e.printStackTrace()
                app().showMessage(R.string.error_io)
                error = true
            } catch (e: Exception) {
                e.printStackTrace()
                app().showMessage(R.string.error_default)
                error = true
            } finally {
            }
        }
    }

    // Это, конечно, очень топорная реализация Observer'а, обладающая рядом очевидных недостатоков.
    // Но использовать здесь имеющуюся у нас будет нарушением NDA, а писать новую потянет на
    // отдельное тестовое задание
    private val dataChangeListeners = LinkedList<DataChangeListener>()

    fun addDataChangeListener(listener: DataChangeListener) {
        synchronized(dataChangeListeners) {
            dataChangeListeners.add(listener)
        }
    }

    fun removeDataChangeListener(listener: DataChangeListener) {
        synchronized(dataChangeListeners) {
            dataChangeListeners.remove(listener)
        }
    }

    private fun onDataChange() {
        val listeners = synchronized(dataChangeListeners) {
            if (dataChangeListeners.isEmpty())
                return
            ArrayList<DataChangeListener>(dataChangeListeners)
        }

        for (listener in listeners) {
            listener.onCurrencyDataChange()
        }
    }

    interface DataChangeListener {
        fun onCurrencyDataChange()
    }
}
