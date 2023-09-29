package com.aryasurya.githubapp.data.localsetting.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aryasurya.githubapp.data.localsetting.entity.ThemeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThemeDao {
    @Query("SELECT * FROM theme_settings WHERE id = 1")
    fun getThemeSetting(): Flow<ThemeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveThemeSetting(themeEntity: ThemeEntity)
}