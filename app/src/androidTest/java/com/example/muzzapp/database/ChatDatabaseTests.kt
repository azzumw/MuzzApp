package com.example.muzzapp.database

import androidx.room.Room
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.example.muzzapp.model.Message
import com.example.muzzapp.ui.User
import com.example.muzzapp.util.MainCoroutineRule
import com.example.muzzapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.hasItems
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

@ExperimentalCoroutinesApi
class ChatDatabaseTests {

    //subject under test
    private lateinit var database: ChatDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

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
    fun insertMessage_saves_message_in_database() = runTest {
        //GIVEN - an instance of Message
        val date = Date().time
        val message = Message("Hello", User.ME.ordinal, date)

        //WHEN - message is inserted in database
        database.chatDao().insertMessage(message)

        // THEN - verify it is in the database
        val result = database.chatDao().getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(result?.size, `is`(1))
        MatcherAssert.assertThat(result?.first()?.sender, `is`(0))
        MatcherAssert.assertThat(result?.first()?.messageText, `is`("Hello"))
    }

    @Test
    fun getAllMessages_returns_all_the_messages() = runTest {
        // GIVEN: a list of messages
        val messages = listOf(
            Message("Hello", User.ME.ordinal, 20000L),
            Message("How are you?", User.YOU.ordinal, 30000L),
            Message("Are you there?", User.ME.ordinal, 40000L),
        )

        //saved in the database
        database.chatDao().addAllMessages(messages)

        // WHEN: getMessages is called
        val messageList = database.chatDao().getAllMessages().getOrAwaitValue()

        // THEN: verify all size of the database table is 3
        // messages are stored in the database.
        MatcherAssert.assertThat(messageList?.size, `is`(3))
        MatcherAssert.assertThat(
            messageList,
            hasItems(messages.first(), messages[1], messages.last())
        )
    }

    @Test
    fun clearMessages_clears_all_the_messages() = runTest {
        //GIVEN - a list a messages
        val messages = listOf(
            Message("Hello", User.ME.ordinal, 20000L),
            Message("How are you?", User.YOU.ordinal, 30000L),
            Message("Are you there?", User.ME.ordinal, 40000L),
        )

        //saved in the database
        database.chatDao().addAllMessages(messages)

        // check they are stored in the database
        val messageList = database.chatDao().getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(messageList?.size, `is`(3))
        MatcherAssert.assertThat(
            messageList,
            hasItems(messages.first(), messages[1], messages.last())
        )

        //WHEN - database is cleared
        database.chatDao().clearMessages()

        // THEN - verify database has no messages.
        val result = database.chatDao().getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(result?.size, `is`(0))
    }
}