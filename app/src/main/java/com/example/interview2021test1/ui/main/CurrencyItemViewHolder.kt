package com.example.interview2021test1.ui.main

import android.annotation.SuppressLint
import android.view.View
import com.example.interview2021test1.databinding.ItemCurrencyBinding
import com.example.interview2021test1.model.types.Currency

class CurrencyItemViewHolder(itemView: View) : AbsViewHolder(itemView) {

    companion object {
        val ICON_COLORS: Array<Int> = arrayOf(
            0xffff0000.toInt(),
            0xff00ff00.toInt(),
            0xff0000ff.toInt(),
            0xffff00ff.toInt(),
            0xffffff00.toInt(),
            0xff0000ff.toInt()
        )
    }

    val binding = ItemCurrencyBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    override fun bind(data: Any?) {
        data as Currency
        binding.icon.text = data.symbol
        binding.icon.setTextColor(ICON_COLORS[(data.symbol.hashCode() and 0x7fffffff) % ICON_COLORS.size])

        binding.title.text = data.name

        binding.subtitle.text = "$${data.price}"

        if (data.percentChange1h >= 0) {
            binding.change.text = "+${data.percentChange1h}"
            binding.change.setTextColor(0xFFABEDAB.toInt())
        } else {
            binding.change.text = data.percentChange1h.toString()
            binding.change.setTextColor(0xFFEDABAB.toInt())
        }
    }
}