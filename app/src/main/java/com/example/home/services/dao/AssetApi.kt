package com.example.home.services.dao

import com.example.home.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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
    suspend fun latestDeviceStatus(@Path("deviceId") deviceId: String): Response<LastStatus>

    @GET("company/company-date-parameter/companyId/{companyId}")
    suspend fun dateParams(@Path("companyId") companyId: String): Response<DateParams>

    @GET("device/deviceLogHistory/deviceId/{deviceId}/startDate/{startDate}/endDate/{endDate}")
    suspend fun logStatus(
        @Path("deviceId") deviceId: String,
        @Path("startDate") startDate: String,
        @Path("endDate") endDate: String,
    ): Response<LastStatus>

    @GET("charts/time-series/deviceId/{deviceId}/companyId/{companyId}/startDevNetwkTime/{startTime}/stopDevNetwkTime/{endTime}")
    suspend fun summarizedChart(
        @Path("deviceId") deviceId: String,
        @Path("companyId") companyId: String,
        @Path("startTime") startTime: String,
        @Path("endTime") endTime: String,
    ): Response<List<Chart>>

    @GET("charts/static-charts/deviceId/{deviceId}/companyId/{companyId}")
    suspend fun staticChart(
        @Path("deviceId") deviceId: String,
        @Path("companyId") companyId: String,
    ):Response<List<Chart>>

    @POST("setup/archilogic/get-access-token")
    suspend fun tempToken(): Response<TempKey>

}