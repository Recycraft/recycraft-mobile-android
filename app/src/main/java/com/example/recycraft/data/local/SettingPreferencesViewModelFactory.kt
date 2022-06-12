package com.example.recycraft.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


class SettingPreferencesViewModelFactory(private val pref: SettingPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingPreferencesViewModel::class.java)) {
            return SettingPreferencesViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class: " + modelClass.name)
    }
}