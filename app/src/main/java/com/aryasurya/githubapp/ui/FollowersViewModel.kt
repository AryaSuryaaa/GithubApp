package com.aryasurya.githubapp.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.githubapp.data.remote.response.DetailUserResponse
import com.aryasurya.githubapp.data.remote.response.FollowersResponseItem
import com.aryasurya.githubapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowersViewModel(var username: String): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<FollowersResponseItem>>()
    val listFollowers: LiveData<List<FollowersResponseItem>> = _listFollowers

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    init {
        getUserDetail()
    }

    fun findDataUserFollowers() {
        _isLoading.value = true
        val user = ApiConfig.getApiService().getFollowers(username)
        user.enqueue(object : retrofit2.Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = responseBody
                    }
                } else {
                    Log.d("FollowersViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowersViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun findDataUserFollowing() {
        _isLoading.value = true
        val user = ApiConfig.getApiService().getFollowing(username)
        user.enqueue(object : retrofit2.Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response<List<FollowersResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listFollowers.value = responseBody
                    }
                } else {
                    Log.d("FollowersViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e("FollowersViewModel", "onFailure: ${t.message}")
            }
        })
    }

    fun getUserDetail() {
        _isLoading.value = true
        val userDetail = ApiConfig.getApiService().getDetailUser(username)
        userDetail.enqueue(object : retrofit2.Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _isLoading.value = false
                        _detailUser.value = responseBody
                    } else {
                        Log.d("FollowersViewModel", "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.d("FollowersViewModel", "onFailure: ${t.message}")
            }
        })
    }
}

class FollowersViewModelFactory private constructor(
    private val selectedUser: String
) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: FollowersViewModelFactory? = null

        fun getInstance(context: Context, selectedUser: String): FollowersViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowersViewModelFactory(
                    selectedUser
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FollowersViewModel::class.java)->{
                FollowersViewModel(selectedUser) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}