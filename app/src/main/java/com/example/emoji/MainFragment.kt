package com.example.emoji

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.emoji.databinding.FragmentMainBinding
import com.example.emoji.databinding.FragmentMessageBinding


class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        setupNavigation()

        return binding.root
    }

    private fun setupNavigation() {
        val fragment =
            childFragmentManager.findFragmentById(R.id.main_fragment) as NavHostFragment
        val controller = fragment.findNavController()
        binding.bottomNav.setupWithNavController(controller)
    }
}