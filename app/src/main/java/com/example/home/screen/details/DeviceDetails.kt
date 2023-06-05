package com.example.home.screen.details

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.InsertChart
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.outlined.InsertChart
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.PieChart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.model.DataGroupAttribute
import com.example.home.model.DeviceLog
import com.example.home.model.LastStatus
import com.example.home.model.Response
import com.example.home.screen.home.HomeViewModel
import com.example.home.screen.plan.ShowDevicesOnFloor
import com.example.home.utils.Progress
import com.example.home.utils.deviceAttr
import com.example.home.utils.statusIcon

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
    val phoneDims = LocalContext.current.resources.displayMetrics
    if (lastStatus.loading == true) {
        //TODO loading bar
        Log.d("TAG", "Details: last device status loading")
    } else {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {
            Column() {
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(
                        text = deviceId.split("=")[1],
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h5,
                        modifier = Modifier.padding(top = 7.dp)
                    )
                    Text(text = " - ${lastStatus.data?.response?.messages?.get(0)?.time!!}")
                }

                Spacer(modifier = Modifier.height(20.dp))

                //get the last status details
                val allStatus =
                    lastStatus.data?.response?.messages?.get(0)?.deviceLog?.map { device ->
                        device.dataGroupAttributes.map { attr -> attr }
                    }

                //turn 2d array to one
                val status = allStatus!!.flatMap { it }


                Surface(modifier = Modifier.height(phoneDims.heightPixels.dp.times(0.15f))) {
                    LazyColumn(contentPadding = PaddingValues(5.dp)) {
                        items(items = status) { dataVal ->
                            DevicesInfo(
                                dataVal,

                                )
                        }
                    }
                }

                ChartMenu()
            }

        }
    }
}

@Composable
fun DevicesInfo(data: DataGroupAttribute) {
    Surface() {
        Row() {
            Text(text = data.attribute?.replace("_", " "), modifier = Modifier.weight(4f))
            Row(Modifier.weight(3f)) {
                Text(text = deviceAttr(data))
//                Text(text = statusIcon(data))
                //TODO image icon
            }
        }
    }
}

@Composable
fun ChartMenu() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "Device logs",
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                            .size(60.dp)
                    )
                    Text(text = "Device Logs", fontSize = 14.sp)
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =
                    Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.InsertChart,
                        contentDescription = "Chart",
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                            .size(60.dp)
                            .clickable { })
                    Text(text = "Static Charts", fontSize = 14.sp)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PieChart,
                        contentDescription = "Gauge",
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                            .size(60.dp)
                    )
                    Text(text = "Gauge", fontSize = 14.sp)
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier =
                    Modifier.weight(1f),
                ) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = "Map",
                        modifier = Modifier
                            .padding(bottom = 3.dp)
                            .size(60.dp)
                            .clickable { })
                    Text(text = "View location", fontSize = 14.sp)
                }
            }

        }
    }
}

