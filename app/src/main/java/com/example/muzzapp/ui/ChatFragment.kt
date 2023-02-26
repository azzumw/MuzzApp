package com.example.muzzapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.muzzapp.ChatApplication
import com.example.muzzapp.adapter.ChatAdapter
import com.example.muzzapp.databinding.FragmentChatBinding
import com.example.muzzapp.model.Message
import java.util.Calendar


class ChatFragment : Fragment() {

    private var _binding:FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel:ChatViewModel  by viewModels{
        ChatViewModelFactory((requireActivity().application as ChatApplication)
            .database.chatDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentChatBinding.inflate(inflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.chatRecyclerView

        val adapter = ChatAdapter()
        recyclerView.adapter = adapter

        binding.sendButton.setOnClickListener {
            val isTextNotBlank =  binding.editMessagebox.text.isNotBlank()
            if(!isTextNotBlank) return@setOnClickListener

            //create message instance
            //add new message to the list
            //update the list
            val txt = binding.editMessagebox.text.toString()

            //add message to the data
//            chatViewModel.insertMessage()

            adapter.messages += Message(txt, timestamp = Calendar.getInstance().timeInMillis)

            binding.editMessagebox.text.clear()

            recyclerView.scrollToPosition(adapter.messages.lastIndex)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}