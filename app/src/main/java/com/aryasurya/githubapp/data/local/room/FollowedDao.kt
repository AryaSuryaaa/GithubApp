package com.aryasurya.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aryasurya.githubapp.data.local.entity.FollowedEntity

@Dao
interface FollowedDao {

    @Query("SELECT * FROM follow ORDER BY login ASC")
    fun getAllFollowed(): LiveData<List<FollowedEntity>>

    @Query("SELECT * FROM follow WHERE isFollowed = 1")
    fun getIsFollwed(): LiveData<List<FollowedEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(followed: List<FollowedEntity>)

    @Update
    fun update(followed: FollowedEntity)

    @Query("DELETE FROM follow WHERE isFollowed = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM follow WHERE login = :login AND isFollowed = 1)")
    fun isUserFollowed(login: String): Boolean




}