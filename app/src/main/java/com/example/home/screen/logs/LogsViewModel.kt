package com.example.home.screen.logs

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.home.model.LastStatus
import com.example.home.model.Response
import com.example.home.services.repository.AssetRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(private val repo: AssetRepo) : ViewModel() {
    suspend fun deviceLogs(
        deviceId: String,
        start: String,
        end: String,
    ): Progress<Response<LastStatus>, Boolean, Exception> {
        return repo.deviceLog(deviceId = deviceId, startDate = start, endDate = end)
    }
}