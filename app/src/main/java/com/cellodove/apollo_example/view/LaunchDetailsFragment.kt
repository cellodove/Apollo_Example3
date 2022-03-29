package com.cellodove.apollo_example.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.cellodove.apollo_example.databinding.LaunchDetailsFragmentBinding
import com.cellodove.apollo_example.databinding.LaunchListFragmentBinding
import com.cellodove.apollo_example.viewmodel.MainViewModel

class LaunchDetailsFragment : Fragment() {
    private lateinit var binding: LaunchDetailsFragmentBinding
    private val viewModel : MainViewModel by activityViewModels()
    val args: LaunchDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelObserver()

        lifecycleScope.launchWhenResumed {
            binding.bookButton.visibility = View.GONE
            binding.bookProgressBar.visibility = View.GONE
            binding.progressBar.visibility = View.VISIBLE
            binding.error.visibility = View.GONE

            viewModel.getLaunchDetailQuery(args.launchId)
        }

    }

    private fun viewModelObserver(){
        viewModel.launchDetailQueryData.observe(viewLifecycleOwner){ response ->
            if (response.data?.launch == null || response.hasErrors()){
                binding.progressBar.visibility = View.GONE
                binding.error.text = response.errors?.get(0)?.message
                binding.error.visibility = View.VISIBLE
            }
        }
        viewModel.errorLiveData.observe(viewLifecycleOwner){ e ->
            binding.progressBar.visibility = View.GONE
            binding.error.text = "Oh no... A protocol error happened"
            binding.error.visibility = View.VISIBLE
        }

    }
}