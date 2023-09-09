package com.aryasurya.githubapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.data.response.DetailUserResponse
import com.aryasurya.githubapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var followersViewModel:FollowersViewModel

    companion object {
        private const val TAG = "Detail Activity"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        val getUsername = intent.getStringExtra("DATA")
        // getUsername dipaksa tidak null
        followersViewModel =
            ViewModelProvider(this, FollowersViewModelFactory.getInstance(this, getUsername!!))[FollowersViewModel::class.java]

        setContentView(binding.root)

        followersViewModel.detailUser.observe(this) {
            setDataDetail(it)
        }

        val sectionPagerAdapter = SectionsPagerAdapter(this)
        val viewPager = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs = binding.tabsLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    fun setDataDetail(detailData: DetailUserResponse) {
        binding.tvUsernameDetail.text = detailData.login
        binding.tvNameDetail.text = detailData.name.toString()
        binding.tvFollowersDetail.text = getString(R.string.followers_text, detailData.followers)
        binding.tvFollowingDetail.text = getString(R.string.following_text, detailData.following)
        Glide.with(binding.root.context)
            .load(detailData.avatarUrl)
            .into(binding.imgDetailUser)
    }



}