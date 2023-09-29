package com.aryasurya.githubapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aryasurya.githubapp.data.local.entity.Follow

@Database(entities = [Follow::class], version = 1, exportSchema = false)
abstract class FollowDatabase : RoomDatabase(){
    abstract fun follwedDao(): FollowDao

    companion object {
        @Volatile
        private var INSTANCE: FollowDatabase? = null
        fun getInstance(context: Context): FollowDatabase {
            if (INSTANCE == null) {
                synchronized(FollowDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FollowDatabase::class.java, "Followed.db"
                    ).build()
                }
            }
            return INSTANCE as FollowDatabase
        }

    }
}