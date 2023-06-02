package com.example.home.screen.details

import androidx.lifecycle.ViewModel
import com.example.home.model.LastStatus
import com.example.home.model.Response
import com.example.home.services.repository.AssetRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DeviceDetailsViewModel @Inject constructor(private val repo: AssetRepo) : ViewModel() {
    suspend fun lastDeviceStatus(deviceId: String): Progress<Response<LastStatus>, Boolean, Exception> {
        return repo.lastDeviceStatus(deviceId)
    }
}