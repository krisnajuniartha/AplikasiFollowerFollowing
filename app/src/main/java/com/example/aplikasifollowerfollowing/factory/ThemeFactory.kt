package com.example.aplikasifollowerfollowing.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aplikasifollowerfollowing.ui.theme.ThemePreference
import com.example.aplikasifollowerfollowing.ui.theme.ThemeViewModel

class ThemeFactory(private val pref: ThemePreference): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)){
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ThemeViewModel class" + modelClass.name)
    }
}