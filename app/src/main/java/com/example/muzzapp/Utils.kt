package com.example.muzzapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.muzzapp.model.Message
import com.example.muzzapp.ui.adapter.ChatAdapter
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(time: Long): Pair<String, String> {
    val format = SimpleDateFormat("EEEE HH:mm", Locale.UK)
    val formattedDate = format.format(time)
    return Pair(formattedDate.substringBefore(' '), formattedDate.substringAfter(' '))
}

fun showCurrentDayTimestamp(
    currentMessage: Message,
    holder: RecyclerView.ViewHolder
) {
    when (holder) {
        is ChatAdapter.MessageViewHolder -> {
            val time = formatDate(currentMessage.timestamp)
            holder.dayTextView.text = time.first
            holder.timeStampTextView.text = time.second
            holder.dayTimeContainer.visibility = View.VISIBLE
        }
        is ChatAdapter.DataAndTimeSectionViewHolder -> {
            val time = formatDate(currentMessage.timestamp)
            holder.dayTextView.text = time.first
            holder.timeTextView.text = time.second
        }
    }
}

fun showCurrentDayTimestamp(
    holder: ChatAdapter.DataAndTimeSectionViewHolder,
    dayTime: Pair<String, String>
) {
    holder.dayTextView.text = dayTime.first
    holder.timeTextView.text = dayTime.second
}

