package com.cellodove.apollo_example.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.cellodove.apollo_example.databinding.LaunchDetailsFragmentBinding
import com.cellodove.apollo_example.databinding.LaunchListFragmentBinding
import com.cellodove.apollo_example.viewmodel.MainViewModel

class LaunchDetailsFragment : Fragment() {
    private lateinit var binding: LaunchDetailsFragmentBinding
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }
}