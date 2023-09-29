package com.aryasurya.githubapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aryasurya.githubapp.data.local.entity.Follow

@Dao
interface FollowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(follow: Follow)

    @Delete
    fun delete(follow: Follow)

    @Query("SELECT * FROM follow ORDER BY login DESC")
    fun getAllFollows(): LiveData<List<Follow>>
}