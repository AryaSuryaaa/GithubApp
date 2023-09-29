package com.aryasurya.githubapp.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryasurya.githubapp.ui.detailuser.FollowersViewModel
import com.aryasurya.githubapp.ui.followed.FollowedViewModel


// Kelas ini berfungsi untuk menambahkan context ketika memanggil kelas ViewModel di dalam Activity
class FollowersViewModelFactory private constructor(private val selectedUser: String, private val mApplication: Application) :
    ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: FollowersViewModelFactory? = null

        fun getInstance(selectedUser: String, mApplication: Application): FollowersViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: FollowersViewModelFactory(
                    selectedUser, mApplication
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FollowersViewModel::class.java)->{
                FollowersViewModel(selectedUser, mApplication) as T
            }
            modelClass.isAssignableFrom(FollowedViewModel::class.java) -> {
                FollowedViewModel(mApplication) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}