package com.dsy2204.accesapp.settings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class UiSettingsViewModel : ViewModel() {
    val highContrast = mutableStateOf(false)
    val kidsMode = mutableStateOf(false)
}
