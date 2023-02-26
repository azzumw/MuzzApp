package com.example.muzzapp.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.muzzapp.model.Message
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ChatDatabaseTests {


    private lateinit var database: ChatDatabase


    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ChatDatabase::class.java
        ).allowMainThreadQueries()
            .build()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertMessage_savesMessageInDatabase() = runTest {
        //GIVEN - an instance of Message
        val date = Date().time
        val message = Message("Hello", sender = 0, date)

        //WHEN - message is inserted in database
        database.chatDao().insertMessage(message)

        // THEN - verify it is in the database
        val result = database.chatDao().getMessage()

        MatcherAssert.assertThat(result?.messageText, `is`("Hello"))
    }
}