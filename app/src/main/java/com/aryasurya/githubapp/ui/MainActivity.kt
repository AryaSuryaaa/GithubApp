package com.aryasurya.githubapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.data.remote.response.ItemsItem
import com.aryasurya.githubapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private val rotateOpen: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim) }
    private val rotateClose: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim) }
    private val fromBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.from_bottom_anime) }
    private val toBottom: Animation by lazy { AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim) }

    private var clicked = false

    companion object {
        private const val TAG = "MainActivity"
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

                        // Menerima data yang dicari
                        mainViewModel.findUsers(searchView.text.toString())
                        false
                }
        }

        binding.fabMore.setOnClickListener(this)
        binding.fabAccount.setOnClickListener(this)
        binding.fabSetting.setOnClickListener(this)
    }

    private fun setUserData(userLogin: List<ItemsItem?>?) {
        val adapter = UsersAdapter()
        adapter.submitList(userLogin)
        binding.rvUsers.adapter = adapter

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java)
                intentToDetail.putExtra("DATA", data.login)
                startActivity(intentToDetail)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.fab_more -> {
                onMoreButtonClicked()
            }
            R.id.fab_account -> {
                Toast.makeText(this, "Account button clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.fab_setting -> {
                Toast.makeText(this, "Setting button clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onMoreButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            binding.fabAccount.visibility = View.VISIBLE
            binding.fabSetting.visibility = View.VISIBLE
        } else {
            binding.fabAccount.visibility = View.INVISIBLE
            binding.fabSetting.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            binding.fabAccount.startAnimation(toBottom)
            binding.fabSetting.startAnimation(toBottom)
        } else {
            binding.fabAccount.startAnimation(fromBottom)
            binding.fabSetting.startAnimation(fromBottom)
        }
    }
}