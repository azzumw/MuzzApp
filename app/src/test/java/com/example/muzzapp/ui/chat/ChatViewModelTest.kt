package com.example.muzzapp.ui.chat


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.muzzapp.model.Message
import com.example.muzzapp.repository.FakeRepository
import com.example.muzzapp.repository.Repository
import com.example.muzzapp.ui.ChatViewModel
import com.example.muzzapp.util.MainCoroutineRule
import com.example.muzzapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class ChatViewModelTest{

    companion object{
        private const val SENDER_USER = 0
        private const val RECEIVER_USER = 1
    }
    //subject under test
    private lateinit var  chatViewModel: ChatViewModel

    private lateinit var repository: Repository

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        val messages = listOf(
            Message("Hello", SENDER_USER,Calendar.getInstance().timeInMillis),
            Message("How are you?", SENDER_USER,Calendar.getInstance().timeInMillis),
            Message("Are you there?", SENDER_USER,Calendar.getInstance().timeInMillis),
        )
        repository = FakeRepository()
    }

    @After
    fun tearDown() = runBlocking{
        repository.clearMessages()
    }

    @Test
    fun insertMessage() = runTest {
        // GIVEN - a fresh viewModel
        chatViewModel = ChatViewModel(repository)

        //ensure there are no messages in the database
        val messages = repository.getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(messages, `is`(emptyList()))

        val message = Message("Hello", SENDER_USER,Calendar.getInstance().timeInMillis)
        // WHEN : a message is inserted
        chatViewModel.sendMessage("Hello", SENDER_USER)
//        repository.insertMessage(message)
        //THEN - verify the message is successfully inserted in the database
        val resultMessage = repository.getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(resultMessage?.size, `is`(1))

    }
}