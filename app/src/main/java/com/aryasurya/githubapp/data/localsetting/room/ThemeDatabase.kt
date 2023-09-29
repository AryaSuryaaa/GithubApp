package com.aryasurya.githubapp.data.localsetting.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aryasurya.githubapp.data.local.room.FollowDatabase
import com.aryasurya.githubapp.data.localsetting.entity.ThemeEntity

@Database(entities = [ThemeEntity::class], version = 1)
abstract class ThemeDatabase : RoomDatabase() {
    abstract fun themeDao(): ThemeDao

    companion object {
        @Volatile
        private var INSTANCE: ThemeDatabase? = null
        fun getInstance(context: Context): ThemeDatabase {
            if (INSTANCE == null) {
                synchronized(ThemeDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        ThemeDatabase::class.java, "theme.db"
                    ).build()
                }
            }
            return INSTANCE as ThemeDatabase
        }

    }
}