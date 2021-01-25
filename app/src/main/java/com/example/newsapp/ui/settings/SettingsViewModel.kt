package com.example.newsapp.ui.settings

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.newsapp.repository.Repository

class SettingsViewModel @ViewModelInject constructor(
    private val repository: Repository
) : ViewModel() {

    fun getCountryPreference() = repository.getCountryCode()

    fun saveCountryPreference(country: String) {
        repository.setCountryCode(country)
    }
}