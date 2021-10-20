package com.example.emoji.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.emoji.R
import com.example.emoji.databinding.FragmentMessageBinding
import com.example.emoji.fragments.delegateItem.DateDelegate
import com.example.emoji.fragments.delegateItem.MainAdapter
import com.example.emoji.fragments.delegateItem.UserDelegate
import com.example.emoji.model.MessageModel
import com.example.emoji.stub.UserFactory
import com.example.emoji.support.toDelegateItemListWithDate


class MessageFragment : Fragment() {
    private val usersStub = UserFactory().getUsers()

    private lateinit var mainAdapter: MainAdapter

    private var _binding: FragmentMessageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMessageBinding.inflate(layoutInflater)

        initAdapter()
        setupSendPanel()
        setupSendingMessage()

        return binding.root
    }

    private fun setupSendPanel() {
        binding.sendPanel.apply {
            sendButton.isEnabled = false
            enterMessageEt.doOnTextChanged { text, start, before, count ->
                if (enterMessageEt.text.toString().isEmpty()) {
                    sendButton.setImageDrawable(requireContext().getDrawable(R.drawable.delete))
                    sendButton.isEnabled = false
                } else {
                    sendButton.setImageDrawable(requireContext().getDrawable(R.drawable.ic_delete))
                    sendButton.isEnabled = true
                }
            }
        }
    }

    private fun setupSendingMessage() {

        binding.sendPanel.sendButton.setOnClickListener {
 
            hideKeyboard()
            usersStub.add(
                MessageModel(
                    "Me",
                    binding.sendPanel.enterMessageEt.text.toString(),
                    R.drawable.doge,
                    "25",
                    "feb",
                    true
                )
            )
            binding.sendPanel.enterMessageEt.setText("")

            mainAdapter.submitList(usersStub.toDelegateItemListWithDate())
         }
    }

    private fun hideKeyboard() {
        val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        inputManager.hideSoftInputFromWindow(
            binding.sendPanel.enterMessageEt.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS
        )
    }

    private fun initAdapter() {
        mainAdapter = MainAdapter()

        mainAdapter.apply {
            addDelegate(UserDelegate(requireActivity().supportFragmentManager))
            addDelegate(DateDelegate())
        }
        binding.recycleMessage.adapter = mainAdapter
         mainAdapter.submitList(usersStub.toDelegateItemListWithDate())
     }

}