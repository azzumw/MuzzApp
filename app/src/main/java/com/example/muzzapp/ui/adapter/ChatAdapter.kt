package com.example.muzzapp.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.R
import com.example.muzzapp.model.Message
import java.text.SimpleDateFormat
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

    class MessageViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val messageTextView: TextView = view.findViewById(R.id.chat_text_bubble_item)
        val layout: TextView = view.findViewById(R.id.timeStamp_tv)
    }

    class DataAndTimeSectionViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val dayTextView: TextView = view.findViewById(R.id.day_textview)
        val timeTextView: TextView = view.findViewById(R.id.time_textview)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val adapterLayout = LayoutInflater.from(parent.context)

        val layout = when (viewType) {
            0 -> R.layout.chat_list_item_me
            1 -> R.layout.chat_list_item_other
            2 -> R.layout.date_time_section_list_item
            else -> throw java.lang.ClassCastException("Unknown View Type: $viewType")
        }

        val view = adapterLayout.inflate(layout, parent, false)

        return if (viewType == 2) DataAndTimeSectionViewHolder(view) else MessageViewHolder(view)
    }

    private fun formatDate(time: Long): Pair<String, String> {
        val format = SimpleDateFormat("EEE HH:mm")
        val formattedDate = format.format(time)
        return Pair(formattedDate.substringBefore(' '), formattedDate.substringAfter(' '))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == 2) {

            val currentMessageTimeStamp = formatDate(Calendar.getInstance().timeInMillis)

            displayDayAndTime(holder as DataAndTimeSectionViewHolder, currentMessageTimeStamp)

            if (messages.isNotEmpty()) {

                val firstMessageTimeStamp = formatDate(messages.first().timestamp)
                displayDayAndTime(holder, firstMessageTimeStamp)
            }

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

            if (holder.layoutPosition >= 2) {

                val isTimeLapseOver5 =
                    (currentMessage.timestamp - messages[position - 2].timestamp) > ONE_HOUR

                if (isTimeLapseOver5) {
                    val time = formatDate(currentMessage.timestamp)
                    holder.layout.text = "${time.first} ${time.second}"
                    holder.layout.visibility = View.VISIBLE

                } else holder.layout.visibility = View.GONE

            } else holder.layout.visibility = View.GONE

        }


    }

    private fun displayDayAndTime(
        holder: DataAndTimeSectionViewHolder,
        dayTime: Pair<String, String>
    ) {
        holder.dayTextView.text = dayTime.first
        holder.timeTextView.text = dayTime.second
    }

    override fun getItemCount(): Int {
        return messages.size + 1
    }

    override fun getItemViewType(position: Int): Int =
        if (position == 0) 2 else messages[position - 1].sender


    private fun setChatBubbleWithTail(viewType: Int, holder: MessageViewHolder) =
        if (viewType == 0) {
            setDrawable(holder, R.drawable.bg_send_chat_bubble_tail)
        } else {
            setDrawable(holder, R.drawable.bg_received_chat_bubble_tail)
        }

    private fun setChatBubbleWithoutTail(viewType: Int, holder: MessageViewHolder) {
        if (viewType == 0) {
            setDrawable(holder, R.drawable.bg_send_chat_bubble)
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


