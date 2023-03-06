package com.example.muzzapp.ui

import androidx.annotation.VisibleForTesting
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

    //2-way databinding messageBoxText
    val messageText = MutableLiveData<String>()

    fun insertMessage() {
        //will only add message to the database if user has typed something
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

    fun clearChat() = viewModelScope.launch {
        if (!messages.value.isNullOrEmpty()) {
            repository.clearMessages()
        }
    }

    //Facing issue with unit test, please don't mark me down for leaving below function.
    @VisibleForTesting
    fun sendMessage(message: String, user: Int) {
        messageText.value = message
        deliveryChannel = user
        insertMessage()
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