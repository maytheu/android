package com.example.home.screen.logs

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.home.component.Expandable
import com.example.home.component.Layout
import com.example.home.model.LastStatus
import com.example.home.model.Response
import com.example.home.screen.home.HomeViewModel
import com.example.home.utils.Progress
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogsScreen(
    homeViewModel: HomeViewModel,
    deviceId: String,
    navController: NavHostController,
    logsViewModel: LogsViewModel,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = user[0].firstName,
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            DeviceLogs(deviceId, logsViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeviceLogs(deviceId: String, logsViewModel: LogsViewModel) {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val previous10Days = currentDate.minusDays(10)

    val logs = produceState<Progress<Response<LastStatus>, Boolean, Exception>>(
        initialValue = Progress(loading = true)
    ) {
        value = logsViewModel.deviceLogs(
            deviceId = deviceId.split("=")[0],
            end = "$currentDate 23:59:59",
            start = "2022-12-08 00:00:00"
        )
    }.value

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Column() {
            Text(
                text = deviceId.split("=")[1],
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(top = 7.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            if (logs.loading == true) {
                //TODO loading bar
                Log.d("TAG", "Details: last device status loading")
            } else {
                val allLogs = mutableListOf<Map<String, Any>>()

                for (msg in logs.data?.response?.messages!!) {
                    val attr = msg.deviceLog.flatMap { it.dataGroupAttributes }
                    val dataGroupAttr = attr.associate { it.attribute to it.attributeValue }
                    val firstMap = mapOf(
                        "time" to msg.time,
                        "sequence" to msg.seqNumber,
                        "title" to msg.deviceLog[0].dataGroupAttributes[0].attribute,
                        "type" to msg.deviceLog[0].dataGroup,
                        "data" to msg.deviceLog[0].dataGroupAttributes.map { attr-> "${attr.attribute.replace("_"," ")}: ${attr.attributeValue}" }
                    )

                    val secondMap = mapOf(
                        "time" to msg.time,
                        "sequence" to msg.seqNumber,
                        "title" to "Atlas network position",
                        "type" to msg.deviceLog[1].dataGroup,
                        "data" to msg.deviceLog[1].dataGroupAttributes.map { attr-> "${attr.attribute.replace("_"," ")}: ${attr.attributeValue}" }
                    )
                    allLogs.add(firstMap)
                    allLogs.add(secondMap)
                }



                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = Color(0xFFEEF1EF),
                    shape = RoundedCornerShape(20.dp),
                ) {
                    LazyColumn(
                        modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
                    ) {
                        items(items = allLogs) { deviceStatus ->
                            DeviceExpandableCard(deviceStatus)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DeviceExpandableCard(device: Map<String, Any>) {
    val phoneDims = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0xFFEEF1EF),
    ) {
        Expandable(expanded = false, title = "${device["title"]}") {
            Surface(
                modifier = Modifier
                    .height(phoneDims.heightPixels.dp.times(0.1f))
                    .fillMaxWidth()
            ) {
                //reformat the attribute type and value
                val data = device["data"].toString().split(",")

                LazyColumn(modifier = Modifier.padding(5.dp)) {
                    items(items = data){
                        Text(text = it.replace("[","").replace("]",""))
                    }
                    item{
                        Text(text = "Time: ${device["time"]}")
                        Text(text = "Data Type: ${device["type"]}")
                        Text(text = "Sequence: ${device["sequence"]}")

                    }
                }
            }
        }
    }

}

