package com.maytheu.weather.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.model.Setting
import com.maytheu.weather.repository.WeatherDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherSettingsViewModel @Inject constructor(private val weatherDBRepo: WeatherDBRepo) :
    ViewModel() {
    private val _unit = MutableStateFlow<List<Setting>>(emptyList())
    val units = _unit.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepo.getUnit().distinctUntilChanged().collect { unit ->
                if (unit.isNullOrEmpty()) {
                    Log.d("Settings vm", "no units: ")
                } else {
                    _unit.value = unit
                }
            }
        }
    }

    fun addUnit(unit: Setting) = viewModelScope.launch {
        weatherDBRepo.addUnit(unit)
    }

    fun updateUnit(unit: Setting) = viewModelScope.launch { weatherDBRepo.updateSettings(unit) }

    fun deleteAll() = viewModelScope.launch { weatherDBRepo.deleteAllUnit() }
}