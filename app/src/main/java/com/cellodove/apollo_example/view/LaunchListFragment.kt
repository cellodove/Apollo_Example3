package com.cellodove.apollo_example.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.databinding.LaunchListFragmentBinding
import com.cellodove.apollo_example.repository.Apollo
import com.cellodove.apollo_example.viewmodel.MainViewModel
import kotlinx.coroutines.channels.Channel

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding
    private val viewModel : MainViewModel by activityViewModels()

    private val launches = mutableListOf<LaunchListQuery.Launch>()
    private val adapter = LaunchListAdapter(launches)
    var cursor: String? = null
    private val channel = Channel<Unit>(Channel.CONFLATED)

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
        viewModelObserver()
        binding.launches.adapter = adapter
        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }
        viewModel.getLaunchListQuery(cursor,requireContext())

        lifecycleScope.launchWhenResumed {
            for (item in channel) {
                binding.showProgress.visibility = View.VISIBLE
                viewModel.getLaunchListQuery(cursor,requireContext())
            }
            adapter.onEndOfListReached = null
            channel.close()
        }

        adapter.onItemClicked = { launch ->
            findNavController().navigate(
                LaunchListFragmentDirections.openLaunchDetails(launchId = launch.id)
            )
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun viewModelObserver(){
        viewModel.launchListQueryData.observe(viewLifecycleOwner){ response ->
            binding.showProgress.visibility = View.GONE
            Log.e("response","${response.data}")
            val newLaunches = response.data?.launches?.launches?.filterNotNull()
            if (newLaunches != null && !response.hasErrors()){
                launches.addAll(newLaunches)
                adapter.notifyDataSetChanged()
            }
            cursor = response.data?.launches?.cursor
        }
    }
}