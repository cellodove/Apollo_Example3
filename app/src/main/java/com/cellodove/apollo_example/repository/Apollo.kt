package com.cellodove.apollo_example.repository

import com.apollographql.apollo3.ApolloClient

object Apollo {
    fun apolloClient():ApolloClient{
        return ApolloClient.Builder()
            .serverUrl("https://apollo-fullstack-tutorial.herokuapp.com/graphql")
            .build()
    }
}