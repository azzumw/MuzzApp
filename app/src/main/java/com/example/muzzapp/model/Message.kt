package com.example.muzzapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "message")
data class Message(
    val messageText: String,
    var sender: Int = 0,
    @PrimaryKey val timestamp: Long
)

/*
probably not an ideal primary key. would use auto-gen instead.
* */