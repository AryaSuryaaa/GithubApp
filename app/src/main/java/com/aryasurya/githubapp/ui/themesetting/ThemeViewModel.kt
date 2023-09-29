package com.aryasurya.githubapp.ui.themesetting

import android.app.Application
import androidx.lifecycle.ViewModel
import com.aryasurya.githubapp.data.local.Repository
import com.aryasurya.githubapp.data.localsetting.ThemeRepository
import com.aryasurya.githubapp.data.localsetting.entity.ThemeEntity
import com.aryasurya.githubapp.data.localsetting.room.ThemeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeViewModel(application: Application): ViewModel() {

    private val mThemeRepository: ThemeRepository = ThemeRepository(application)

    fun getThemeSetting(): Flow<Boolean> = mThemeRepository.getThemeSetting()

    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        mThemeRepository.saveThemeSetting(isDarkModeActive)
    }
}