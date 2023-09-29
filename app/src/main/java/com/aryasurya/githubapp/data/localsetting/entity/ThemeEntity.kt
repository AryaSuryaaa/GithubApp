package com.aryasurya.githubapp.data.localsetting.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theme_settings")
data class ThemeEntity(
    @PrimaryKey val id: Int = 1,
    val isDarkModeActive: Boolean
)