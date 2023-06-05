package com.example.home.screen.chart

import androidx.lifecycle.ViewModel
import com.example.home.services.repository.AssetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChartViewModel @Inject constructor(private val repo:AssetRepo):ViewModel() {
}