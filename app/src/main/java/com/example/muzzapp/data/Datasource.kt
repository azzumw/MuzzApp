package com.example.muzzapp.data

import com.example.muzzapp.R
import com.example.muzzapp.model.Message

class Datasource {

    fun loadMessages(): List<Message> {
        return listOf(
            Message(R.string.affirmation1),
            Message(R.string.affirmation2),
            Message(R.string.affirmation3),
            Message(R.string.affirmation4),
            Message(R.string.affirmation5),
            Message(R.string.affirmation6),
            Message(R.string.affirmation7),
            Message(R.string.affirmation8),
            Message(R.string.affirmation9),
            Message(R.string.affirmation10)
        )
    }
}