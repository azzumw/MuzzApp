package com.example.muzzapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.muzzapp.model.Message
import com.example.muzzapp.repository.Repository
import kotlinx.coroutines.launch
import java.util.*

var deliveryChannel: Int = User.ME.ordinal

enum class User {
    ME, YOU;

    val switch: User
        get() = when (this) {
            ME -> YOU
            YOU -> ME
        }
}


class ChatViewModel(private val repository: Repository) : ViewModel() {

    val messages: LiveData<List<Message>?> = repository.getAllMessages()

    val messageText = MutableLiveData<String>()

    fun insertMessage() {
        if (!validateInput()) {
            val message =
                Message(messageText.value!!, deliveryChannel, Calendar.getInstance().timeInMillis)

            viewModelScope.launch {
                repository.insertMessage(message)
            }
        }

        messageText.value = ""
    }

    private fun validateInput() = messageText.value.isNullOrBlank()

    fun clear() = viewModelScope.launch {
        if (!messages.value.isNullOrEmpty()) {
            repository.clearMessages()
        }
    }
}

class ChatViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}