package com.cellodove.apollo_example.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.repository.Apollo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var launchListQueryData = MutableLiveData<ApolloResponse<LaunchListQuery.Data>>()

    fun getLaunchListQuery() = GlobalScope.launch{
        try {
            launchListQueryData.postValue(Apollo.apolloClient().query(LaunchListQuery()).execute())
        }catch (e: ApolloException){
            Log.e("LaunchList", "Failure", e)
            return@launch
        }
    }

}