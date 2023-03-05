package com.example.muzzapp.database

import androidx.room.Room
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.muzzapp.model.Message
import com.example.muzzapp.util.MainCoroutineRule
import com.example.muzzapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
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
    fun insertMessage_savesMessageInDatabase() = runTest {
        //GIVEN - an instance of Message
        val date = Date().time
        val message = Message("Hello", sender = 0, date)

        //WHEN - message is inserted in database
        database.chatDao().insertMessage(message)

        // THEN - verify it is in the database
        val result = database.chatDao().getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(result?.size, `is`(1))
    }






}