package com.example.rocketreserver

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.example.rocketreserver.databinding.LaunchDetailsFragmentBinding

class LaunchDetailsFragment : Fragment() {

    private lateinit var binding: LaunchDetailsFragmentBinding
    val args: LaunchDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = LaunchDetailsFragmentBinding.inflate(inflater)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenResumed {
            val apolloClient = ApolloClient.builder()
                .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com")
                .build()

            val response = apolloClient.query(LaunchListQuery()).await()

            Log.d("LaunchList", "Success ${response?.data}")
        }
    }

}