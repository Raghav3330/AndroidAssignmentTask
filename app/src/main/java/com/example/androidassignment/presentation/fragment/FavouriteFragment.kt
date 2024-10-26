package com.example.androidassignment.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import com.example.androidassignment.MainActivity
import com.example.androidassignment.databinding.FragmentFavouriteBinding

class FavouriteFragment : Fragment() {

    private lateinit var binding: FragmentFavouriteBinding

    private var isCreate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreate = true
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouriteBinding.inflate(layoutInflater)

        val favouriteViewModel by activityViewModels<FavouriteViewModel>()
        binding.tvActivityStatus.text = favouriteViewModel.statusText


        binding.tvActivityStatus.setOnClickListener {
            favouriteViewModel.statusText = "Resume"
            binding.tvActivityStatus.text = favouriteViewModel.statusText
            MainActivity.INSTANCE?.openOrders()
        }
        // Inflate the layout for this fragment
        return binding.root
    }
}

class FavouriteViewModel : ViewModel() {
    var statusText: String = "Start" // Set initial default text
}
