package com.example.muzzapp.adapter

import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.model.Message

class ChatAdapter(private val context: Context) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    var messages: List<Message> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)

        val layout = if (viewType == 0) {
            R.layout.chat_list_item_me
        } else {
            R.layout.chat_list_item_other
        }

        Log.e("OnCreateViewholder", messages.size.toString())
        Log.e("OnCreateView-Itemcountr", itemCount.toString())
        val view = adapterLayout.inflate(layout, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val currentMessage = messages[position]
        holder.messageTextView.text = currentMessage.messageText


        Log.e("Adapter-Current", currentMessage.messageText)
        Log.e("Adapter-pos", position.toString())
//        Log.e("Adapter-LM", lastMessage.messageText)
//        Log.e("Adapter-LM-Pos", (position-1).toString())

        when (position) {
            0 -> {
                holder.messageTextView.setTextColor(context.resources.getColor(R.color.orange))
                when(getItemViewType(position)){
                    0 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_send_chat_bubble_tail)
                    1 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_received_chat_bubble_tail)
                }
            }
            else -> {
                val lastMessage = messages[position - 1]
                if (lastMessage.sender == currentMessage.sender) {
                    when(getItemViewType(position)){
                        0 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_send_chat_bubble)
                        1 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_received_chat_bubble)
                    }
                    holder.messageTextView.setTextColor(context.resources.getColor(R.color.blue))
                    holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_send_chat_bubble)

                } else {
                    holder.messageTextView.setTextColor(context.resources.getColor(R.color.orange))
                    holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_send_chat_bubble)
                    when(getItemViewType(position)){
                        0 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_send_chat_bubble_tail)
                        1 -> holder.messageTextView.background = context.resources.getDrawable(R.drawable.bg_received_chat_bubble_tail)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int) = messages[position].sender

}
