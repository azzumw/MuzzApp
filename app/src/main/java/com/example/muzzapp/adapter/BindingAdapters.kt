package com.example.muzzapp.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.model.Message

@BindingAdapter("listData")
fun bindChatRecyclerView(recyclerView: RecyclerView,messages:List<Message>?){
    val adapter = recyclerView.adapter as ChatAdapter
    messages?.let {
        adapter.messages = it
        recyclerView.scrollToPosition(it.lastIndex+1)
    }
}