package com.example.recycraft.data.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.recycraft.data.remote.Session
import kotlinx.coroutines.launch


class SettingPreferencesViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getLoginSettings(): LiveData<Session> {
        return pref.getLoginSetting().asLiveData()
    }

    fun saveLoginSetting(isLogin: Boolean, token: String) {
        viewModelScope.launch {
            pref.saveLoginSetting(isLogin, token)
        }
    }
}