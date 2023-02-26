package com.example.muzzapp

import android.app.Application
import com.example.muzzapp.database.ChatDatabase

class ChatApplication:Application() {
    val database: ChatDatabase by lazy { ChatDatabase.getDatabase(this) }
}