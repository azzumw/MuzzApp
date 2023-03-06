package com.example.muzzapp.ui.chat


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.muzzapp.model.Message
import com.example.muzzapp.repository.FakeRepository
import com.example.muzzapp.repository.Repository
import com.example.muzzapp.ui.ChatViewModel
import com.example.muzzapp.util.MainCoroutineRule
import com.example.muzzapp.util.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Calendar


@ExperimentalCoroutinesApi
class ChatViewModelTest{

    companion object{
        private const val SENDER_USER = 0
        private const val RECEIVER_USER = 1
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //subject under test
    private lateinit var  chatViewModel: ChatViewModel

    private lateinit var repository: Repository


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
    fun `when message is inserted messages list is updated`() = mainCoroutineRule.runBlockingTest {
        // GIVEN - a fresh viewModel
        chatViewModel = ChatViewModel(repository)

        //ensure there are no messages in the database
        val messages = repository.getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(messages, `is`(emptyList()))

        // WHEN : a message is inserted
        chatViewModel.sendMessage("Hello", SENDER_USER)

        //THEN - verify the message is successfully inserted in the database
        val resultMessage = chatViewModel.messages.getOrAwaitValue()
        MatcherAssert.assertThat(resultMessage?.size, `is`(1))

    }
}