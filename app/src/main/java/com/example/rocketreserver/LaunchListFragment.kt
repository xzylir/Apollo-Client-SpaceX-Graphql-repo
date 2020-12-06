package com.example.rocketreserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.rocketreserver.databinding.LaunchListFragmentBinding
import kotlinx.coroutines.launch

class LaunchListFragment : Fragment() {
    private lateinit var binding: LaunchListFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchListFragmentBinding.inflate(inflater)

        lifecycleScope.launchWhenResumed {
            val response = try{
                val apolloClient = ApolloClient.builder()
                    .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
                    .build()
                apolloClient.query(LaunchListQuery()).await()
            } catch(e: ApolloException){
                Log.d("LaunchList", "Failure", e)
                null
            }

            val launches = response?.data?.launches?.launches?.filterNotNull()
            if (launches != null && !response.hasErrors()){
                val adapter = LaunchListAdapter(launches)
                binding.launches.layoutManager = LinearLayoutManager(requireContext())
                binding.launches.adapter = adapter
            }
        }

        return binding.root

    }

}