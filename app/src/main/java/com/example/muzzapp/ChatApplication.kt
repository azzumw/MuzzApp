package com.example.muzzapp

import android.app.Application
import com.example.muzzapp.database.ChatDatabase
import com.example.muzzapp.repository.Repository
import com.example.muzzapp.repository.RepositoryImpl

class ChatApplication : Application() {
     val database: ChatDatabase by lazy { ChatDatabase.getDatabase(this) }

}