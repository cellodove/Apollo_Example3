package com.cellodove.apollo_example.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.apollographql.apollo3.exception.ApolloException
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.databinding.LaunchListFragmentBinding
import com.cellodove.apollo_example.repository.Apollo
import com.cellodove.apollo_example.viewmodel.MainViewModel

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding
    private val viewModel : MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LaunchListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLaunchListQuery()
        viewModelObserver()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelObserver(){
        viewModel.launchListQueryData.observe(viewLifecycleOwner){ response ->
            binding.showProgress.visibility = View.GONE
            val launches = response.data?.launches?.launches?.filterNotNull()
            if (launches != null && !response.hasErrors()){
                val adapter = LaunchListAdapter(launches)
                binding.launches.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }
    }
}