package com.aryasurya.githubapp.data.localsetting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.aryasurya.githubapp.data.local.room.FollowDatabase
import com.aryasurya.githubapp.data.localsetting.entity.ThemeEntity
import com.aryasurya.githubapp.data.localsetting.room.ThemeDao
import com.aryasurya.githubapp.data.localsetting.room.ThemeDatabase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepository(application: Application) {
    private var themeDao: ThemeDao

    init {
        var db = ThemeDatabase.getInstance(application)
        themeDao = db.themeDao()
    }

    fun getThemeSetting(): LiveData<Boolean> {
        return themeDao.getThemeSetting().map { it?.isDarkModeActive ?: false }.asLiveData()
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        val themeSettings = ThemeEntity(id = 1, isDarkModeActive)
        themeDao.saveThemeSetting(themeSettings)
    }
}
