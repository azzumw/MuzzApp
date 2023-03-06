package com.example.muzzapp.ui.chat


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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

@ExperimentalCoroutinesApi
class ChatViewModelTest{

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    //subject under test
    private lateinit var  chatViewModel: ChatViewModel

    private lateinit var repository: Repository


    @Before
    fun setUp() {
        repository = FakeRepository()
    }

    @After
    fun tearDown() = runBlocking{
        repository.clearMessages()
    }


    @Test
    fun `insertMessage inserts message and messages list is updated`() = mainCoroutineRule.runBlockingTest {
        // GIVEN - a fresh viewModel
        chatViewModel = ChatViewModel(repository)

        //ensure there are no messages in the database
        val messages = repository.getAllMessages().getOrAwaitValue()
        MatcherAssert.assertThat(messages, `is`(emptyList()))

        // WHEN : a message is inserted
        chatViewModel.messageText.value = "Hello"
        chatViewModel.insertMessage()

        //THEN - verify the message is successfully inserted in the database
        // and messages livedata is updated
        val resultMessage = chatViewModel.messages.getOrAwaitValue()
        MatcherAssert.assertThat(resultMessage?.size, `is`(1))

    }

    @Test
    fun `clearChat clears the messages list`() = mainCoroutineRule.runBlockingTest{
        // GIVEN - a fresh viewModel
        chatViewModel = ChatViewModel(repository)

        // with a message in the repository
        chatViewModel.messageText.value = "Hello"
        chatViewModel.insertMessage()

        // check the messages list is not empty
        val messages = chatViewModel.messages.getOrAwaitValue()
        MatcherAssert.assertThat(messages?.size, `is`(1))

        // WHEN - chat is cleared
        chatViewModel.clearChat()

        // THEN - verify messages list is empty
        val result = chatViewModel.messages.getOrAwaitValue()
        MatcherAssert.assertThat(result?.size, `is`(0))

    }
}