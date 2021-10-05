package com.example.interview2021test1.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.interview2021test1.R
import com.example.interview2021test1.model

class HomeScreenAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val PREFETCH_TRIGGER = 20
    }

    private var inflater: LayoutInflater? = null
    private var lastRequestedSize = 0

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        inflater = LayoutInflater.from(recyclerView.context)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        inflater = null
    }

    override fun getItemViewType(position: Int) =
        if (position < model().currency.count)
            R.layout.item_currency
        else
            R.layout.item_progress

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            R.layout.item_currency ->
                CurrencyItemViewHolder(inflater!!.inflate(viewType, parent, false))
            else ->
                ProgressViewHolder(inflater!!.inflate(R.layout.item_progress, parent, false))
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as AbsViewHolder

        val data = when (holder.itemViewType) {
            R.layout.item_currency -> model().currency.getItem(position)
            else -> null
        }
        holder.bind(data)

        if (!model().currency.eofReached
            && lastRequestedSize != model().currency.count
            && position >= (model().currency.count - PREFETCH_TRIGGER)) {
            lastRequestedSize = model().currency.count
            model().currency.requestNextPage()
        }
    }

    override fun getItemCount(): Int {
        var count = model().currency.count
        if (!model().currency.eofReached)
            count++
        return count
    }


}
