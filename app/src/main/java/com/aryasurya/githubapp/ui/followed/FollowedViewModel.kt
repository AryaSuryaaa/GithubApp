package com.aryasurya.githubapp.ui.followed

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aryasurya.githubapp.data.local.Repository
import com.aryasurya.githubapp.data.local.entity.Follow

class FollowedViewModel(application: Application) : ViewModel() {
    private val mFollowedRepository: Repository = Repository(application)

    fun getAllFollowed(): LiveData<List<Follow>> = mFollowedRepository.getAllFollows()
}