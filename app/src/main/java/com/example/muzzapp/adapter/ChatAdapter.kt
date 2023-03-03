package com.example.muzzapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.adapter.DataItem.Header
import com.example.muzzapp.model.Message
import java.util.Calendar

private const val TWENTY_SECONDS = 20000
private const val FIVE_SECONDS = 5000
private const val ONE_HOUR = 3600000

const val TAG = "ChatAd: "

class ChatAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messages: List<Message> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    fun submitListA(list: List<Message>) {
        when (list) {
            null -> listOf(Header)
            else -> listOf(Header) + messages
        }
    }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
        val layout = view.findViewById<View>(R.id.date_time_layout)
    }

    class DataAndTimeSectionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.day_textview)
        val timeTextView: TextView = view.findViewById(R.id.time_textview)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)

        val layout1 = when (viewType) {
            0 -> R.layout.chat_list_item_me
            1 -> R.layout.chat_list_item_other
            2 -> R.layout.date_time_section_list_item
            else -> throw java.lang.ClassCastException("Unknown View Type: $viewType")
        }

        val view = adapterLayout.inflate(layout1, parent, false)

        return if (viewType == 2) DataAndTimeSectionViewHolder(view) else MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 2) {
            (holder as DataAndTimeSectionViewHolder).dayTextView.text =
                Calendar.getInstance().time.toString()
            Log.e("$TAG onBindView posD:", position.toString())
        } else {
            val currentMessage = messages[position - 1]
            (holder as MessageViewHolder).messageTextView.text = currentMessage.messageText

            Log.e("$TAG Message size:", messages.size.toString())
            Log.e("$TAG Messages Last index:", messages.lastIndex.toString())
            Log.e("$TAG Current Message:", currentMessage.messageText)
            Log.e("$TAG onBindView pos:", position.toString())

            val isLastMessage = messages.lastIndex == position - 1

            if (isLastMessage) {
                setChatBubbleWithTail(getItemViewType(position), holder)
            } else {
                val nextMessage = messages[position]
                val isDifferentSender = nextMessage.sender != currentMessage.sender
                val isTimeLapseOver20 =
                    (nextMessage.timestamp - currentMessage.timestamp) > TWENTY_SECONDS

                if (isDifferentSender || isTimeLapseOver20) {
                    setChatBubbleWithTail(getItemViewType(position), holder)

                } else {
                    setChatBubbleWithoutTail(getItemViewType(position), holder)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return messages.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            Log.e("$TAG getItemView pos", position.toString())
            return 2
        }

//        if(position > 1){
//            if((messages[position-1].timestamp-messages[position-2].timestamp)> FIVE_SECONDS){
//                return 2
//            } else return messages[position-1].sender
//
//        }
        return messages[position - 1].sender
    }

    private fun setChatBubbleWithTail(viewType: Int, holder: ChatAdapter.MessageViewHolder) =
        if (viewType == 0) {
            setDrawable(holder, R.drawable.bg_send_chat_bubble_tail)
        } else {
            setDrawable(holder, R.drawable.bg_received_chat_bubble_tail)
        }

    private fun setChatBubbleWithoutTail(viewType: Int, holder: ChatAdapter.MessageViewHolder) {
        if (viewType == 0) {
            setDrawable(holder, R.drawable.bg_send_chat_bubble)
        } else {
            setDrawable(holder, R.drawable.bg_received_chat_bubble)
        }
    }

    private fun setDrawable(holder: ChatAdapter.MessageViewHolder, resourceId: Int) {
        holder.messageTextView.background = ResourcesCompat.getDrawable(
            context.resources,
            resourceId,
            null
        )
    }

}

sealed class DataItem {
    data class MessageSendItem(val message: Message) : DataItem()
    data class MessageReceiveItem(val message: Message) : DataItem()
    object Header : DataItem() {
        val time: Long = Calendar.getInstance().timeInMillis
    }
}


