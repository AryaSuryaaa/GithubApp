package com.aryasurya.githubapp.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.githubapp.data.FollowedRepository
import com.aryasurya.githubapp.ui.detailuser.FollowersViewModel

class FollowersViewModelFactory private constructor(
    private val selectedUser: String,
    private val followedRepository: FollowedRepository
) :
    ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: FollowersViewModelFactory? = null

        fun getInstance(context: Context, selectedUser: String): FollowersViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowersViewModelFactory(
                    selectedUser, FollowedRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FollowersViewModel::class.java)->{
                FollowersViewModel(selectedUser, followedRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}