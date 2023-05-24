package com.example.home.screen.plan

import androidx.lifecycle.ViewModel
import com.example.home.model.TempKey
import com.example.home.services.repository.AssetRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(private val repo: AssetRepo) : ViewModel() {
    suspend fun getTempKey(): Progress<TempKey, Boolean, Exception> {
        return repo.getTempKey()
    }
}