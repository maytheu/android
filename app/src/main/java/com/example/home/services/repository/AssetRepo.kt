package com.example.home.services.repository

import com.example.home.model.*
import com.example.home.services.dao.AssetApi
import com.example.home.utils.Progress
import javax.inject.Inject

class AssetRepo @Inject constructor(private val api: AssetApi) {
    private val _tempToken = Progress<TempKey, Boolean, Exception>()

    suspend fun companyAssets(companyId: String): Progress<Response<List<Asset>>, Boolean, Exception> {
        val resp = try {
            api.loadCompanyAssets(companyId)
        } catch (e: Exception) {
            return Progress(e = e)
        }
        return Progress(resp)
    }

    suspend fun assetDevices(
        assetId: String,
        companyId: String,
    ): Progress<Response<List<Floor>>, Boolean, Exception> {
        val resp = try {
            api.loadDevicesOnFloor(assetId = assetId, companyId = companyId)
        } catch (e: Exception) {
            return Progress(e = e)
        }
        return Progress(resp)
    }

    suspend fun getTempKey(): Progress<TempKey, Boolean, Exception> {
        try {
            _tempToken.loading = true
            _tempToken.data = api.tempToken().response
            if (_tempToken.data.toString().isNotEmpty()) _tempToken.loading = false
        } catch (e: Exception) {
            _tempToken.e = e
        }
        return _tempToken
    }

    suspend fun lastDeviceStatus(deviceId: String): Progress<Response<LastStatus>, Boolean, Exception> {
        val resp = try {
            api.latestDeviceStatus(deviceId)
        } catch (e:Exception) {
            return Progress(e = e)
        }
        return Progress(resp)
    }




}