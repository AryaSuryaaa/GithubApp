package com.aryasurya.githubapp.ui.themesetting

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aryasurya.githubapp.data.localsetting.ThemeRepository
import kotlinx.coroutines.launch

class ThemeViewModel(application: Application): ViewModel() {

    private val mThemeRepository: ThemeRepository = ThemeRepository(application)

    fun getThemeSetting(): LiveData<Boolean> = mThemeRepository.getThemeSetting()

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            mThemeRepository.saveThemeSetting(isDarkModeActive)
        }
    }
}