package com.example.home.screen.chart

import androidx.lifecycle.ViewModel
import com.example.home.model.Response
import com.example.home.model.SingleChartItem
import com.example.home.services.repository.AssetRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(private val repo:AssetRepo):ViewModel() {
    suspend fun viewChart(
        deviceId: String,
        start: String,
        end: String,
    ): Progress<Response<List<SingleChartItem>>, Boolean, Exception> {
        return repo.summarizedChart(deviceId = deviceId, startDate = start, endDate = end)
    }}