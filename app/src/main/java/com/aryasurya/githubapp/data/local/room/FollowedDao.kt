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

    @Query("SELECT * FROM follow ORDER BY login DESC")
    fun getFollwedUser(): LiveData<List<FollowedEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(followedEntity: FollowedEntity)

    @Delete
    fun deleteFavoriteUser(followedEntity: FollowedEntity)

}