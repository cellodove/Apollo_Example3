package com.cellodove.apollo_example.repository

import android.content.Context
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.okHttpClient
import com.cellodove.apollo_example.model.User
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

object Apollo {
    fun apolloClient(context: Context):ApolloClient{
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor(context))
            .build()

        return ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .webSocketServerUrl("wss://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .okHttpClient(okHttpClient)
            .build()
    }

    private class AuthorizationInterceptor(val context: Context): Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", User.getToken(context) ?: "")
                .build()
            return chain.proceed(request)
        }
    }
}