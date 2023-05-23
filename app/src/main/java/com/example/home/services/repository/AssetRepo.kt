package com.example.home.services.repository

import com.example.home.model.Asset
import com.example.home.model.Floor
import com.example.home.model.Response
import com.example.home.services.dao.AssetApi
import com.example.home.utils.Progress
import javax.inject.Inject

class AssetRepo @Inject constructor(private val api: AssetApi) {

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
            api.loadDevicesOnFloor(assetId=assetId, companyId=companyId)
        } catch (e: Exception) {
            return Progress(e = e)
        }
        return Progress(resp)
    }
}