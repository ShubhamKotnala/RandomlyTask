package com.seven.myapplication.network

import com.seven.myapplication.model.FeedResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIClient {
    //59ac293b100000d60bf9c239 59b3f0b0100000e30b236b7e
    @GET
    suspend fun getFeeds(@Url url: String?): FeedResponse
}
