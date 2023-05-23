package com.example.home.screen.floor

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.home.model.Device
import com.example.home.model.Floor
import com.example.home.model.FloorInfo
import com.example.home.model.Response
import com.example.home.services.repository.AssetRepo
import com.example.home.utils.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FloorViewModel @Inject constructor(private val api: AssetRepo) : ViewModel() {
    var loading: Boolean by mutableStateOf(true)
    private val _devices: MutableState<Progress<Response<List<Floor>>, Boolean, Exception>> =
        mutableStateOf(Progress(null, true, Exception("")))

    val devices = _devices


    fun loadAssetDevices(
        assetId: String,
        companyId: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (companyId.isEmpty() && assetId.isEmpty()) return@launch
            _devices.value = api.assetDevices(assetId = assetId, companyId)
            if (devices.value.data != null) {
                _devices.value.loading = false
            }
        }
    }
}