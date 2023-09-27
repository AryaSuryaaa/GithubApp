package com.aryasurya.githubapp.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.githubapp.data.FollowedRepository
import com.aryasurya.githubapp.data.local.entity.FollowedEntity
import com.aryasurya.githubapp.data.remote.response.DetailUserResponse
import com.aryasurya.githubapp.data.remote.response.FollowersResponseItem
import com.aryasurya.githubapp.data.remote.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class FollowersViewModel(var username: String, var followedRepository: FollowedRepository): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listFollowers = MutableLiveData<List<FollowersResponseItem>?>()
    val listFollowers: LiveData<List<FollowersResponseItem>?> = _listFollowers

    private val _detailUser = MutableLiveData<DetailUserResponse?>()
    val detailUser: LiveData<DetailUserResponse?> = _detailUser

    private val _thisFollowed = MutableLiveData<Boolean>()
    val thisFollowed: LiveData<Boolean> = _thisFollowed

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


    fun setThisFollowed(boolean: Boolean) {
        _thisFollowed.value = boolean
    }
    fun insertFollowed(followedEntity: FollowedEntity) {
        setThisFollowed(true)
        followedRepository.insertFollowed(followedEntity)
    }

    fun deleteFollowed(followedEntity: FollowedEntity) {
        setThisFollowed(false)
        followedRepository.deleteFollowed(followedEntity)
    }


    fun updateFollowed(followed: FollowedEntity) {
        if (thisFollowed.value != true) {
            insertFollowed(followed)
        } else {
            deleteFollowed(followed)
        }
    }

fun getAllFollowed():LiveData<List<FollowedEntity>> = followedRepository.getAllFollowed()



//    fun getUserDetail() = followedRepository.getDetailUser(username)
//
//    fun getFollowedUser() = followedRepository.getFollowedUser()
//
//    fun saveFollow(followedEntity: FollowedEntity) {
//        // Di dalam view Model ga boleh suspend
//        viewModelScope.launch {
//            followedRepository.setFollowUser(followedEntity, true)
//        }
//    }
//
//    fun deleteFollow(followedEntity: FollowedEntity) {
//        // Di dalam view Model ga boleh suspend
//        viewModelScope.launch {
//            followedRepository.setFollowUser(followedEntity, false)
//        }
//    }
}

