package com.example.muzzapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.*
import com.example.muzzapp.model.Message
import java.util.*

private const val TWENTY_SECONDS = 20000
private const val ONE_HOUR = 3600000
private const val MESSAGE_SEND_TYPE = 0
private const val MESSAGE_RECEIVE_TYPE = 1
private const val HEADER_TYPE = 2


class ChatAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var messages: List<Message> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
        val dayTimeContainer: LinearLayout = view.findViewById(R.id.day_time_container)
        val dayTextView: TextView = view.findViewById(R.id.day_text_view)
        val timeStampTextView: TextView = view.findViewById(R.id.timestamp_text_view)
    }

    class DataAndTimeSectionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.day_textview)
        val timeTextView: TextView = view.findViewById(R.id.time_textview)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)

        val layout = when (viewType) {
            MESSAGE_SEND_TYPE -> R.layout.chat_list_item_me
            MESSAGE_RECEIVE_TYPE -> R.layout.chat_list_item_other
            HEADER_TYPE -> R.layout.date_time_section_list_item
            else -> throw java.lang.ClassCastException("Unknown View Type: $viewType")
        }

        val view = adapterLayout.inflate(layout, parent, false)

        return if (viewType == HEADER_TYPE) DataAndTimeSectionViewHolder(view) else MessageViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == HEADER_TYPE) {

            if (messages.isNotEmpty()) {
                showCurrentDayTimestamp(messages.first(), holder as DataAndTimeSectionViewHolder)
            } else {
                val currentTimeStamp = formatDate(Calendar.getInstance().timeInMillis)
                showCurrentDayTimestamp(
                    holder as DataAndTimeSectionViewHolder,
                    currentTimeStamp
                )
            }

        } else {

            val currentMessage = messages[position - 1]
            (holder as MessageViewHolder).messageTextView.text = currentMessage.messageText

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

            /*
            when the previous message was sent over an hour ago, show the day and time,
            else keep the day time view to GONE
            * */
            when {
                holder.layoutPosition >= 2 -> {
                    val isTimeLapseOverAnHour =
                        (currentMessage.timestamp - messages[position - 2].timestamp) > ONE_HOUR

                    if (isTimeLapseOverAnHour) {
                        showCurrentDayTimestamp(currentMessage, holder)
                    } else holder.dayTimeContainer.visibility = View.GONE
                }
                else -> holder.dayTimeContainer.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = messages.size + 1

    override fun getItemViewType(position: Int): Int =
        if (position == 0) HEADER_TYPE else messages[position - 1].sender

    private fun setChatBubbleWithTail(viewType: Int, holder: ChatAdapter.MessageViewHolder) =
        if (viewType == MESSAGE_SEND_TYPE) {
            setDrawable(holder, R.drawable.bg_send_chat_bubble_tail)
        } else {
            setDrawable(holder, R.drawable.bg_received_chat_bubble_tail)
        }

    private fun setChatBubbleWithoutTail(viewType: Int, holder: ChatAdapter.MessageViewHolder) {
        if (viewType == MESSAGE_SEND_TYPE) {
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


