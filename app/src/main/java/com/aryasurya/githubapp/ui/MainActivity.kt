package com.aryasurya.githubapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.data.response.GithubResponse
import com.aryasurya.githubapp.data.response.ItemsItem
import com.aryasurya.githubapp.data.retrofit.ApiConfig
import com.aryasurya.githubapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    companion object {
        private const val TAG = "MainActivity"
        private const val USER_LOGIN = "aryasuryaa"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        mainViewModel.listProfile.observe(this) {
            setUserData(it)
        }
        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { v, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    Log.d(TAG, "ini yang dicari : ${searchBar.text}")
//                    findUsers(searchView.text.toString())
                    mainViewModel.findUsers(searchView.text.toString())
                    false
                }
        }
    }

    fun setUserData(userLogin: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(userLogin)
        binding.rvUsers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}