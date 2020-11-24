package com.chilik1020.hw8.util

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


@BindingAdapter("setAdapter")
fun RecyclerView.bindRecyclerViewAdapter(adapter : RecyclerView.Adapter<*>){
    this.run {
//        this.setHasFixedSize(true)
        this.adapter = adapter
    }
}