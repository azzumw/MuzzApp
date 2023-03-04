package com.example.muzzapp.repository

import androidx.lifecycle.LiveData
import com.example.muzzapp.model.Message

interface Repository {

    suspend fun insertMessage(message: Message)

    suspend fun clearMessages()

    fun getAllMessages():LiveData<List<Message>?>

    suspend fun getMessage(): Message?
}