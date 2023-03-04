package com.example.muzzapp.repository

import androidx.lifecycle.LiveData
import com.example.muzzapp.database.ChatDao
import com.example.muzzapp.model.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryImpl(private val chatDao: ChatDao):Repository {

    override suspend fun insertMessage(message: Message) {
        withContext(Dispatchers.IO){
            chatDao.insertMessage(message)
        }
    }

    override fun getAllMessages(): LiveData<List<Message>?> {
        return chatDao.getAllMessages()
    }

    override suspend fun clearMessages() {
       withContext(Dispatchers.IO){
           chatDao.clearMessages()
       }
    }

    override suspend fun getMessage(): Message? {
        return chatDao.getMessage()
    }
}