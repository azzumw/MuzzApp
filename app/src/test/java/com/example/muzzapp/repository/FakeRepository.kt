package com.example.muzzapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.muzzapp.model.Message

class FakeRepository(private var list: MutableList<Message>? = mutableListOf()) : Repository {

    private val _messages = MutableLiveData<List<Message>>(list)
    val messages: LiveData<List<Message>?> get() = _messages


    override suspend fun insertMessage(message: Message) {
        list?.add(message)
        _messages.value = list
    }

    override suspend fun clearMessages() {
        list?.clear()
    }

    override fun getAllMessages(): LiveData<List<Message>?> = messages

}