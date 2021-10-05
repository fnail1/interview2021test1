package com.example.interview2021test1.ui.main

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AbsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(data: Any?)
}