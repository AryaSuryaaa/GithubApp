package com.aryasurya.githubapp.data.retrofit

import com.aryasurya.githubapp.data.response.DetailUserResponse
import com.aryasurya.githubapp.data.response.FollowersResponseItem
import com.aryasurya.githubapp.data.response.GithubResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUsers(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<FollowersResponseItem>>
    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<FollowersResponseItem>>


}