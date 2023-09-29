package com.aryasurya.githubapp.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.aryasurya.githubapp.data.local.entity.FollowedEntity
import com.aryasurya.githubapp.data.local.room.FollowedDao
import com.aryasurya.githubapp.data.local.room.FollowedDatabase
import com.aryasurya.githubapp.data.remote.response.DetailUserResponse
import com.aryasurya.githubapp.data.remote.retrofit.ApiConfig
import com.aryasurya.githubapp.data.remote.retrofit.ApiService
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FollowedRepository private constructor(
    application: Application,
    private val apiService: ApiService,
    private var followedDao: FollowedDao,
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
) {

    init {
        val db = FollowedDatabase.getInstance(application)
        followedDao = db.follwedDao()
    }

    fun getAllFollowed(): LiveData<List<FollowedEntity>> = followedDao.getFollwedUser()

    fun insertFollowed(followedEntity: FollowedEntity) {
        Log.d("Insert", "insertFollowed: ${followedEntity.login}")
        executorService.execute { followedDao.insertFavoriteUser(followedEntity) }
    }

    fun deleteFollowed(followedEntity: FollowedEntity) {
        executorService.execute{ followedDao.deleteFavoriteUser(followedEntity)}
    }

    companion object {
        @Volatile
        private var instance: FollowedRepository? = null
        fun getInstance(context: Context): FollowedRepository {
            return instance ?: synchronized(FollowedRepository::class.java) {
                if (instance == null) {
                    val database = FollowedDatabase.getInstance(context)
                    val api = ApiConfig.getApiService()
                    instance = FollowedRepository(api, database.follwedDao())
                }
                return instance as FollowedRepository
            }
        }
    }
}