package com.aryasurya.githubapp.data.retrofit

import com.aryasurya.githubapp.data.response.GithubResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") q: String
    ): Call<GithubResponse>
}