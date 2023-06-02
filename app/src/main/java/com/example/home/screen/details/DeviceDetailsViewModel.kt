package com.example.home.screen.details

import androidx.lifecycle.ViewModel
import com.example.home.services.repository.AssetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(private val repo: AssetRepo) : ViewModel() {
}