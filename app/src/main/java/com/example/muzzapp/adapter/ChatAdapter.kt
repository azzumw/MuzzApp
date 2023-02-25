package com.example.muzzapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.model.Message

private const val SENT = 0
private const val RECEIVED = 1

class ChatAdapter :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    var messages: List<Message> = listOf<Message>()
        set(value) {
            field = value
            notifyItemInserted(messages.lastIndex)
        }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view:View

        val adapterLayout = LayoutInflater.from(parent.context)
        view = if (viewType == SENT){
            adapterLayout.inflate(R.layout.chat_list_item_me, parent, false)
        }else {
            adapterLayout.inflate(R.layout.chat_list_item_other, parent, false)
        }

        return MessageViewHolder(view)
    }

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messages[position]

        holder.messageTextView.text = currentMessage.messageText
    }

    override fun getItemViewType(position: Int) = if (messages[position].sent == 0) SENT else RECEIVED

}
