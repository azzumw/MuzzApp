package com.example.muzzapp.ui.chat

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.muzzapp.ChatApplication
import com.example.muzzapp.MainActivity
import com.example.muzzapp.R
import com.example.muzzapp.ui.adapter.ChatAdapter
import com.example.muzzapp.databinding.FragmentChatBinding
import com.example.muzzapp.ui.ChatViewModel
import com.example.muzzapp.ui.ChatViewModelFactory
import com.example.muzzapp.ui.User
import com.example.muzzapp.ui.deliveryChannel

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!

    private val chatViewModel: ChatViewModel by viewModels {
        ChatViewModelFactory(
            (requireActivity().application as ChatApplication).repository
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

        binding.apply {
            lifecycleOwner = this@ChatFragment.viewLifecycleOwner
            viewModel = chatViewModel
        }

        /*
     * this has issue with Integration test ChatFragmentTest
     * Because there is no activity attached, hence it throw NPE exception in the test
     * You would have to comment out ln 49-67 to run the test
     * */
        val switcher = requireActivity().findViewById<SwitchCompat>(R.id.user_switch)

        switcher.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                (activity as MainActivity).supportActionBar?.title =
                    getString(R.string.frag_title_user_name_me)
                deliveryChannel = User.ME.switch.ordinal
            } else {

                (activity as MainActivity).supportActionBar?.title =
                    getString(R.string.frag_title_user_name_you)
                deliveryChannel = User.YOU.switch.ordinal
            }
        }

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
                chatViewModel.clearChat()
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