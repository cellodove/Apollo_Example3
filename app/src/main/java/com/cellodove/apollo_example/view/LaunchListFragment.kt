package com.cellodove.apollo_example.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.databinding.LaunchListFragmentBinding
import com.cellodove.apollo_example.repository.Apollo
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding

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

/*        val launches = mutableListOf<LaunchListQuery.Launch>()
        val adapter = LaunchListAdapter(launches)
        binding.launches.adapter = adapter

        val channel = Channel<Unit>(Channel.CONFLATED)

        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }*/

        lifecycleScope.launchWhenResumed {
            var response = try {
                Apollo.apolloClient().query(LaunchListQuery()).execute()
            }catch (e:ApolloException){
                Log.e("LaunchList", "Failure", e)
                return@launchWhenResumed
            }

            val launches = response.data?.launches?.launches?.filterNotNull()
            if (launches != null && !response.hasErrors()){
                val adapter = LaunchListAdapter(launches)
                binding.launches.adapter = adapter
                adapter.notifyDataSetChanged()
            }




/*            var cursor : String ? = null
            for (item in channel){
                var response = try {
                    Apollo.apolloClient(requireContext()).query(LaunchListQuery(Optional.Present(cursor))).execute()
                }catch (e:ApolloException){
                    Log.e("LaunchList", "Failure", e)
                    return@launchWhenResumed
                }

                val newLaunches = response.data?.launches?.launches?.filterNotNull()
                if (newLaunches != null){
                    launches.addAll(newLaunches)
                    adapter.notifyDataSetChanged()
                }
                cursor = response.data?.launches?.cursor
                if (response.data?.launches?.hasMore != true){
                    break
                }
            }
            adapter.onEndOfListReached = null
            channel.close()*/
        }
/*        adapter.onItemClicked = { launch ->
            findNavController().navigate(
                LaunchListFragmentDirections.openLaunchDetails(launchId = launch.id)
            )
        }*/

    }


}