package com.cellodove.apollo_example.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.viewmodel.MainViewModel
import com.cellodove.apollo_example.databinding.ActivityMainBinding
import com.cellodove.apollo_example.repository.Apollo

class MainActivity : AppCompatActivity() {
    private val viewModel : MainViewModel by viewModels()
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        lifecycleScope.launchWhenResumed {
//            val response = Apollo.apolloClient(this@MainActivity).query(LaunchListQuery()).execute()
//            Log.e("LaunchList","Success ${response.data}")
//        }
    }

}