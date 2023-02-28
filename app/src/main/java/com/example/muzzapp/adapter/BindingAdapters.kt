package com.example.muzzapp.adapter

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.model.Message

@BindingAdapter("listData")
fun bindChatRecyclerView(recyclerView: RecyclerView,messages:List<Message>?){
    val adapter = recyclerView.adapter as ChatAdapter
    messages?.let {
        adapter.messages = it
        Log.e("Bin-adapter: ",adapter.itemCount.toString())
        Log.e("Bin-adapter: Msg Size: ",messages.size.toString())
        recyclerView.scrollToPosition(it.lastIndex)
    }
}