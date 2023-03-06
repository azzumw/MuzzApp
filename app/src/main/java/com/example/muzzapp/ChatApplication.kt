package com.example.muzzapp

import android.app.Application
import com.example.muzzapp.repository.Repository

class ChatApplication : Application() {
    val repository: Repository
        get() = ServiceLocator.provideChatRepository(this)
}