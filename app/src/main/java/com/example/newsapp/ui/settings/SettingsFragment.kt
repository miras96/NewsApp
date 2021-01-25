package com.example.newsapp.ui.settings

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.example.newsapp.R
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val countryPreference: ListPreference? = findPreference(getString(R.string.preference_key_country))
        countryPreference?.summary = viewModel.getCountryPreference()
        countryPreference?.setOnPreferenceChangeListener { _, newValue ->
            Timber.d(newValue.toString())
            viewModel.saveCountryPreference(newValue.toString())
            countryPreference.summary = newValue.toString()
            true
        }
    }
}