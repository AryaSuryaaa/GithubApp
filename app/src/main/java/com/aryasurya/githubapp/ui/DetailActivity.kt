package com.aryasurya.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.data.response.DetailUserResponse
import com.aryasurya.githubapp.data.retrofit.ApiConfig
import com.aryasurya.githubapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    companion object {
        private const val TAG = "Detail Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getUsername = intent.getStringExtra("DATA")

        if (getUsername != null) {
            getUserDetail(getUsername)
        }

    }


    fun getUserDetail(username: String) {
        val userDetail = ApiConfig.getApiService().getDetailUser(username)
        userDetail.enqueue(object : retrofit2.Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        binding.tvUsernameDetail.text = responseBody.login
                        binding.tvNameDetail.text = responseBody.name.toString()
                        Glide.with(this@DetailActivity)
                            .load(responseBody.avatarUrl)
                            .into(binding.imgDetailUser)
                        binding.tvFollowersDetail.text = resources.getString(R.string.followers_text, responseBody.followers)
                        binding.tvFollowingDetail.text = resources.getString(R.string.following_text, responseBody.following)
                    } else {
                        Log.d(TAG, "onFailure: ${response.message()}")
                    }
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })
    }
}