package com.example.muzzapp

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.example.muzzapp.database.ChatDatabase
import com.example.muzzapp.repository.Repository
import com.example.muzzapp.repository.RepositoryImpl

object ServiceLocator {

    private val lock = Any()

    @Volatile
    private var database:ChatDatabase? = null


    @Volatile
    var repository:Repository? = null
        @VisibleForTesting set

    fun provideChatRepository(context:Context):Repository{
        synchronized(this){
            return repository ?: createChatRepository(context)
        }

    }

    private fun createChatRepository(context: Context):Repository {
        val newRepo = RepositoryImpl(createDatabase(context).chatDao())
        repository = newRepo
        return newRepo
    }

    private fun createDatabase(context:Context): ChatDatabase {
        val db = ChatDatabase.getDatabase(context)
        database = db
        return db
    }

    @VisibleForTesting
    fun resetRepository() {

        synchronized(lock) {
            // Clear all data to avoid test pollution.
            database?.apply {
                clearAllTables()
//                close()
            }
            database = null
            repository = null
        }
    }
}