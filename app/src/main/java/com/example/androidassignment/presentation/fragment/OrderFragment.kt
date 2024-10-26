package com.example.androidassignment.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.androidassignment.MainActivity
import com.example.androidassignment.databinding.FragmentOrderBinding

class OrderFragment : Fragment() {

    private lateinit var binding: FragmentOrderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOrderBinding.inflate(layoutInflater)

        val orderViewModel by activityViewModels<OrderViewModel>()

        binding.tvActivityStatus.text = orderViewModel.statusText

        binding.tvActivityStatus.setOnClickListener {
            orderViewModel.statusText = "Resume"
            binding.tvActivityStatus.text = orderViewModel.statusText
            MainActivity.INSTANCE?.openSettings()
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}

class OrderViewModel : ViewModel() {
    var statusText: String = "Start" // Set initial default text
}