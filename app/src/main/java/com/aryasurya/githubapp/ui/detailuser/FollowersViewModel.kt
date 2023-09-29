package com.aryasurya.githubapp.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryasurya.githubapp.data.local.Repository
import com.aryasurya.githubapp.data.local.entity.Follow
import com.aryasurya.githubapp.data.remote.response.DetailUserResponse
import com.aryasurya.githubapp.data.remote.response.FollowersResponseItem
import com.aryasurya.githubapp.data.remote.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class FollowersViewModel(private var username: String, application: Application): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<FollowersResponseItem>>()
    val listFollowers: LiveData<List<FollowersResponseItem>> = _listFollowers

    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val mFollowRepository: Repository = Repository(application)

    private val _thisFollow = MutableLiveData<Boolean>()
    val thisFollow: LiveData<Boolean> =_thisFollow

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
                        _listFollowers.value = response.body()
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
                        _listFollowers.value = response.body()
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

    private fun getUserDetail() {
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
                        _detailUser.value = response.body()
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

    fun getAllFollows(): LiveData<List<Follow>> = mFollowRepository.getAllFollows()

    fun setThisFollow(thisFollow: Boolean) {
        _thisFollow.value = thisFollow
    }

    fun insertFollow(follow: Follow) {
//        setThisFollow(true)
        mFollowRepository.insert(follow)
    }

    fun deleteFollow(follow: Follow) {
//        setThisFollow(true)
        mFollowRepository.delete(follow)
    }

}
