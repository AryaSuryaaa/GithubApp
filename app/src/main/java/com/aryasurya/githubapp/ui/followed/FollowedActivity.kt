package com.aryasurya.githubapp.ui.followed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.data.remote.response.ItemsItem
import com.aryasurya.githubapp.databinding.ActivityFollowedBinding
import com.aryasurya.githubapp.helper.FollowersViewModelFactory
import com.aryasurya.githubapp.ui.DetailActivity
import com.aryasurya.githubapp.ui.UsersAdapter

class FollowedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFollowedBinding
    private lateinit var followedViewModel: FollowedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFollowedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvFollowed.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvFollowed.addItemDecoration(itemDecoration)

        followedViewModel = ViewModelProvider(this, FollowersViewModelFactory.getInstance("", this.application))[FollowedViewModel::class.java]

        followedViewModel.getAllFollowed().observe(this) { followed ->
            val userListFollowed = arrayListOf<ItemsItem>()

            followed.map { user ->
                val userFollowed = ItemsItem(login = user.login, avatarUrl = user.urlImg)
                userListFollowed.add(userFollowed)
            }
            setUserData(userListFollowed)

        }
    }

    private fun setUserData(userLogin: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(userLogin)
        binding.rvFollowed.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@FollowedActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data.login)
                startActivity(intentToDetail)
            }
        })
    }
}