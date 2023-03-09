package com.maytheu.weather.screens.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.repository.WeatherDBRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherFavouritesViewModel @Inject constructor(private val weatherDBRepo: WeatherDBRepo) :
    ViewModel() {
    private val _favouritesCity = MutableStateFlow<List<Favourites>>(emptyList())
    val favouritesCity = _favouritesCity.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherDBRepo.getFavouritesCity().distinctUntilChanged().collect { favourites ->
                if (favourites.isNullOrEmpty()) {
                    Log.d("TAG", "empty: ")
                } else {
                    _favouritesCity.value = favourites
                    Log.d("TAG", "$favouritesCity: ")
                }

            }
        }
    }

    fun addFavouriteCity(favourite: Favourites) = viewModelScope.launch {
        weatherDBRepo.newFavouriteCity(favourite)
    }

    fun deleteFavouriteCity(favourite: Favourites) = viewModelScope.launch {
        weatherDBRepo.removeFavouriteCity(favourite)
    }

    fun deleteAllFavouriteCity() = viewModelScope.launch {
        weatherDBRepo.deleteAllFavouriteCity()
    }
}