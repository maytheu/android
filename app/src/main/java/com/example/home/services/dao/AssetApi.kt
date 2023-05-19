package com.example.home.services.dao

import com.example.home.model.Asset
import com.example.home.model.Floor
import com.example.home.model.LastStatus
import com.example.home.model.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface AssetApi {
    @GET("asset/asset-list/companyId/{companyId}")
    suspend fun loadCompanyAssets(@Path("companyId") companyId: String): Response<List<Asset>>

    @GET("device/smart-home-dashboard/companyid/{companyId}/assetid/{assetId}")
    suspend fun loadDevicesOnFloor(
        @Path("companyId") companyId: String,
        @Path("assetId") assetId: String,
    ): Response<List<Floor>>

    @GET("device/deviceLogLatest/deviceId/{deviceId}")
    suspend fun latestDeviceStatus(@Path("deviceId") deviceId:String):Response<LastStatus>
}