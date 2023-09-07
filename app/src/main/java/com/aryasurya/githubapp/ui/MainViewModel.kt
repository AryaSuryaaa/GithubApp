package com.aryasurya.githubapp.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryasurya.githubapp.data.response.GithubResponse
import com.aryasurya.githubapp.data.response.ItemsItem
import com.aryasurya.githubapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response

class MainViewModel: ViewModel() {

    // Backing Property
    // dapat mencegah variabel yang bertipe MutableLiveData diubah dari luar class.
    // input data
    private val _listProfile = MutableLiveData<List<ItemsItem>>()
    // output data
    val listProfile: LiveData<List<ItemsItem>> = _listProfile

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
    }
    init {
        findUsers()
    }

    fun findUsers(profile: String = "arif") {
        _isLoading.value = true
        val user = ApiConfig.getApiService().getListUsers(profile)
        user.enqueue(object : retrofit2.Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listProfile.value = responseBody.items as List<ItemsItem>?
                    }
                } else {
                    Log.d(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

}