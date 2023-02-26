package com.example.muzzapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.muzzapp.database.ChatDao
import com.example.muzzapp.model.Message
import kotlinx.coroutines.launch

class ChatViewModel(private val chatDao: ChatDao) : ViewModel() {


    var messages: LiveData<List<Message>?> = chatDao.getAllMessages()

    init {
        getAllMessages()
    }

    private fun getAllMessages() {
        messages = chatDao.getAllMessages()

    }

    fun insertMessage(message: Message) {
        viewModelScope.launch {
            chatDao.insertMessage(message)
        }
    }

    fun clear() = viewModelScope.launch { chatDao.clearMessages() }
}

class ChatViewModelFactory(private val chatDao: ChatDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(chatDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}