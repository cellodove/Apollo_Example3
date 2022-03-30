package com.cellodove.apollo_example.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.cellodove.apollo_example.LaunchDetailsQuery
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.LoginMutation
import com.cellodove.apollo_example.repository.Apollo
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var launchListQueryData = MutableLiveData<ApolloResponse<LaunchListQuery.Data>>()
    var launchDetailQueryData = MutableLiveData<ApolloResponse<LaunchDetailsQuery.Data>>()
    var loginMutationToken = MutableLiveData<ApolloResponse<LoginMutation.Data>>()

    var errorLiveData = MutableLiveData<ApolloException>()

    fun getLaunchListQuery(cursor:String?,context: Context) = GlobalScope.launch{
        try {
            launchListQueryData.postValue(Apollo.apolloClient(context).query(LaunchListQuery(Optional.Present(cursor))).execute())
        }catch (e: ApolloException){
            errorLiveData.postValue(e)
            Log.e("LaunchList", "Failure", e)
            return@launch
        }
    }

    fun getLaunchDetailQuery(id:String,context: Context) = GlobalScope.launch{
        try {
            launchDetailQueryData.postValue(Apollo.apolloClient(context).query(LaunchDetailsQuery(id)).execute())
        }catch (e: ApolloException){
            errorLiveData.postValue(e)
            Log.e("LaunchDetail", "Failure", e)
            return@launch
        }
    }

    fun loginMutation(email:String,context: Context) = GlobalScope.launch{
        try {
            loginMutationToken.postValue(Apollo.apolloClient(context).mutation(LoginMutation(email = email)).execute())
        }catch (e: ApolloException){
            errorLiveData.postValue(e)
            Log.e("LoginMutation", "Failure", e)
            return@launch
        }
    }
}