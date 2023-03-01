package com.example.muzzapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.model.Message

private const val TWENTY_SECONDS = 20000

class ChatAdapter(private val context: Context) :
    RecyclerView.Adapter<ChatAdapter.MessageViewHolder>() {

    var messages: List<Message> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)

        val layout = if (viewType == 0) {
            R.layout.chat_list_item_me
        } else {
            R.layout.chat_list_item_other
        }

        val view = adapterLayout.inflate(layout, parent, false)

        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {

        val currentMessage = messages[position]
        holder.messageTextView.text = currentMessage.messageText

        // has a tail
        val isLastMessage = messages.lastIndex == position
        //sent by the other user
        //message after it was sent 20s afterwards

        if (isLastMessage) {
            //has a tail
            setChatBubbleWithTail(getItemViewType(position), holder)

        } else {
            val nextMessage = messages[position + 1]

            if (nextMessage.sender == currentMessage.sender) {
                //if next message sent after 20 seconds, then this message has a tail
                if ((nextMessage.timestamp - currentMessage.timestamp) > TWENTY_SECONDS) {
                    setChatBubbleWithTail(getItemViewType(position), holder)
                } else {
                    setChatBubbleWithoutTail(getItemViewType(position), holder)
                }

            } else {
                setChatBubbleWithTail(getItemViewType(position), holder)
            }
        }
    }

    override fun getItemCount(): Int = messages.size

    override fun getItemViewType(position: Int) = messages[position].sender

    private fun setChatBubbleWithTail(viewType: Int, holder: MessageViewHolder) =
        if (viewType == 0) {
            setDrawable(holder,R.drawable.bg_send_chat_bubble_tail)
        } else {
            setDrawable(holder,R.drawable.bg_received_chat_bubble_tail)
        }

    private fun setChatBubbleWithoutTail(viewType: Int, holder: MessageViewHolder) {
        if (viewType == 0) {
            setDrawable(holder,R.drawable.bg_send_chat_bubble)
        } else {
            setDrawable(holder, R.drawable.bg_received_chat_bubble)
        }
    }

    private fun setDrawable(holder: MessageViewHolder, resourceId: Int) {
        holder.messageTextView.background = ResourcesCompat.getDrawable(
            context.resources,
            resourceId,
            null
        )
    }

}


