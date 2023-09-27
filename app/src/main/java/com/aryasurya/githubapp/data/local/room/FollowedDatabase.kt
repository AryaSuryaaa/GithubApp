package com.aryasurya.githubapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aryasurya.githubapp.data.local.entity.FollowedEntity

@Database(entities = [FollowedEntity::class], version = 1)
abstract class FollowedDatabase : RoomDatabase(){
    abstract fun follwedDao(): FollowedDao

    companion object {
        @Volatile
        private var instance: FollowedDatabase? = null
        fun getInstance(context: Context): FollowedDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FollowedDatabase::class.java, "Followed.db"
                ).build()
            }
    }
}