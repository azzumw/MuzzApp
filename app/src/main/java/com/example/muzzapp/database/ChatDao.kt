package com.example.muzzapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.muzzapp.model.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface ChatDao {
    //trumpet leaf tea

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("select * FROM message")
    fun getAllMessages():LiveData<List<Message>>

    @Query("delete from message")
    suspend fun clearMessages()

    @Query("select * from message limit 1")
    suspend fun getMessage():Message?
}