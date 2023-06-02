package com.example.home.screen.details

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.model.DeviceLog
import com.example.home.model.LastStatus
import com.example.home.model.Response
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.plan.ShowDevicesOnFloor
import com.example.home.utils.Progress

@Composable
fun DeviceDetails(
    deviceId: String,
    homeViewModel: HomeViewModel,
    deviceDetailsViewModel: DeviceDetailsViewModel,
    navController: NavController,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = user[0].firstName,
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            Details(deviceId, navController, deviceDetailsViewModel)
        }
    }
}

@Composable
fun Details(
    deviceId: String,
    navController: NavController,
    deviceDetailsViewModel: DeviceDetailsViewModel,
) {
    val lastStatus = produceState<Progress<Response<LastStatus>, Boolean, Exception>>(
        initialValue = Progress(loading = true)
    ) {
        value = deviceDetailsViewModel.lastDeviceStatus(deviceId.split("=")[0])
    }.value

    if (lastStatus.loading == true) {
        Log.d("TAG", "Details: last device status loading")
    } else {
        Log.d("TAG", "Details: ${lastStatus.data?.response?.messages}")
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column() {
                Text(
                    text = deviceId.split("=")[1], fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(top = 7.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                LazyColumn(contentPadding = PaddingValues(2.dp)) {
                    lastStatus.data?.response?.messages?.get(0)?.let {
                        items(items = it.deviceLog) { deviceLog ->
                            DevicesInfo(
                                deviceLog,
                                lastStatus.data?.response?.messages?.get(0)?.time!!
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun DevicesInfo(deviceLog: DeviceLog, time: String) {
    Surface(modifier = Modifier.height(200.dp)) {
        LazyColumn() {
            items(deviceLog.dataGroupAttributes) { data ->
                Text(text = data.attribute)
            }
        }
    }
}
