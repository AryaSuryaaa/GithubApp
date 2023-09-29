package com.aryasurya.githubapp.ui.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.githubapp.R
import com.aryasurya.githubapp.data.local.entity.Follow
import com.aryasurya.githubapp.data.remote.response.DetailUserResponse
import com.aryasurya.githubapp.databinding.ActivityDetailBinding
import com.aryasurya.githubapp.helper.FollowersViewModelFactory
import com.aryasurya.githubapp.ui.SectionsPagerAdapter
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var followersViewModel: FollowersViewModel

    companion object {
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
            ViewModelProvider(this, FollowersViewModelFactory.getInstance(getUsername!!, this@DetailActivity.application
            ))[FollowersViewModel::class.java]

        setContentView(binding.root)

        followersViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        followersViewModel.thisFollow.observe(this) {
            setButtonFollow(it)
        }

        followersViewModel.detailUser.observe(this) {detail ->
            setDataDetail(detail)

            binding.btnFollow.setOnClickListener {
                // Perbarui tampilan tombol follow sesuai dengan status terbaru
                val currentThisFollowValue = followersViewModel.thisFollow.value ?: false
                followersViewModel.setThisFollow(!currentThisFollowValue)

                if (currentThisFollowValue) {
                    // Jika sebelumnya telah diikuti (currentThisFollowValue == true), maka hapus dari database lokal
                    followersViewModel.deleteFollow(Follow(detail.login.toString(), detail.avatarUrl.toString()))

                } else {
                    // Jika sebelumnya tidak diikuti (currentThisFollowValue == false), maka tambahkan ke database lokal
                    followersViewModel.insertFollow(Follow(detail.login.toString(), detail.avatarUrl.toString()))
                }
            }

            val sectionPagerAdapter = SectionsPagerAdapter(this)
            val viewPager = binding.viewPager
            viewPager.adapter = sectionPagerAdapter
            val tabs = binding.tabsLayout
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = resources.getString(TAB_TITLES[position])
            }.attach()


        }

        binding.imgArrowBack.setOnClickListener {
            finish()
        }



        followersViewModel.getAllFollows().observe(this) { user ->
            user.forEach {
                if (it.login == getUsername) {
                    followersViewModel.setThisFollow(true)
                    Follow(it.login, it.urlImg)
                }
            }
        }


    }

    private fun setDataDetail(detailData: DetailUserResponse) {
        binding.tvUsernameDetail.text = detailData.login
        binding.tvNameDetail.text = detailData.name.toString()
        binding.tvFollowersDetail.text = detailData.followers.toString()
        binding.tvFollowingDetail.text = detailData.following.toString()
        binding.tvRepoDetail.text = detailData.publicRepos.toString()
        Glide.with(binding.root.context)
            .load(detailData.avatarUrl)
            .into(binding.imgDetailUser)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.GONE
        }
    }

    private fun setButtonFollow(followed: Boolean) {
        val typedValue = TypedValue()
        if (!followed) {
            binding.btnFollow.text = getString(R.string.follow)
            this.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, typedValue, true)
            binding.btnFollow.setBackgroundColor(ContextCompat.getColor(this, typedValue.resourceId))
        } else {
            binding.btnFollow.text = getString(R.string.following)
            this.theme.resolveAttribute(com.google.android.material.R.attr.colorOnSecondary, typedValue, true)
            binding.btnFollow.setBackgroundColor(ContextCompat.getColor(this, typedValue.resourceId))


        }
    }

}