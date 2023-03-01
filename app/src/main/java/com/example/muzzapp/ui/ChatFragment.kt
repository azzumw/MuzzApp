package com.example.muzzapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.muzzapp.ChatApplication
import com.example.muzzapp.R
import com.example.muzzapp.adapter.ChatAdapter
import com.example.muzzapp.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            (requireActivity().application as ChatApplication)
                .database.chatDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chat, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = chatViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.chatRecyclerView

        recyclerView.adapter = ChatAdapter(requireContext())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_chat_id -> {
                chatViewModel.clear()
                true
            }
            R.id.switch_user_id -> {
                deliveryChannel = if (deliveryChannel == 0) {
                    1
                } else {
                    0
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}