package com.aryasurya.githubapp.data.local

import android.app.Application
import androidx.lifecycle.LiveData
import com.aryasurya.githubapp.data.local.entity.Follow
import com.aryasurya.githubapp.data.local.room.FollowDao
import com.aryasurya.githubapp.data.local.room.FollowDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class Repository(application: Application) {
    private val mFollowDao: FollowDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db =FollowDatabase.getInstance(application)
        mFollowDao = db.follwedDao()
    }

    fun getAllFollows(): LiveData<List<Follow>> = mFollowDao.getAllFollows()

    fun insert(follow: Follow) {
        executorService.execute { mFollowDao.insert(follow) }
    }

    fun delete(follow: Follow) {
        executorService.execute { mFollowDao.delete(follow) }
    }

}