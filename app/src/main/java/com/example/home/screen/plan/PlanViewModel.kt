package com.example.home.screen.plan

import androidx.lifecycle.ViewModel
import com.example.home.services.repository.AssetRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(private val api: AssetRepo) : ViewModel() {
}