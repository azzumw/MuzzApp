package com.example.muzzapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import androidx.recyclerview.widget.ListAdapter
import com.example.muzzapp.model.Message

class ChatListAdapter : ListAdapter<Message, ChatListAdapter.ChatListItemViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatListItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)

        val layout = if (viewType == 0) {
            R.layout.chat_list_item_me
        } else {
            R.layout.chat_list_item_other
        }

        val view = adapterLayout.inflate(layout, parent, false)

        return ChatListItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).sender
//        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ChatListItemViewHolder, position: Int) {
        val currentMessage = getItem(position)
        holder.bind(currentMessage)
    }

    class ChatListItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
        fun bind(message: Message){
            messageTextView.text = message.messageText
        }
    }

    companion object{
        private val DiffCallback = object : DiffUtil.ItemCallback<Message>(){
            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.timestamp == newItem.timestamp
            }

        }
    }
}