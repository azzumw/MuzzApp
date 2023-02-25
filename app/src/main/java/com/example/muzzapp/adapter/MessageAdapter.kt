package com.example.muzzapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.model.Message

class MessageAdapter:
    RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    var messages:List<Message> = listOf<Message>()
    set(value) {
        field = value
//        notifyDataSetChanged()
        notifyItemInserted(messages.lastIndex)
    }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView : TextView = view.findViewById(R.id.chat_text_bubble_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.chat_list_item_me,parent,false)

        return MessageViewHolder(adapterLayout)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messages[position]

        holder.messageTextView.text = currentMessage.messageText
    }
}