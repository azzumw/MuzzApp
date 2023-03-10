package com.example.muzzapp.database

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.muzzapp.model.Message

@Dao
interface ChatDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("select * FROM message")
    fun getAllMessages(): LiveData<List<Message>?>

    @Query("delete from message")
    suspend fun clearMessages()

    @VisibleForTesting
    @Insert
    suspend fun addAllMessages(list: List<Message>)
}