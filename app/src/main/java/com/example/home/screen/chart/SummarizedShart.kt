package com.example.home.screen.chart

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.home.component.Layout
import com.example.home.model.Data
import com.example.home.model.Response
import com.example.home.model.SingleChartItem
import com.example.home.screen.home.HomeViewModel
import com.example.home.utils.Progress
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.model.LineData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SummarizedChart(
    homeViewModel: HomeViewModel,
    chartViewModel: ChartViewModel,
    deviceId: String,
    navController: NavHostController,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = user[0].firstName,
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            DeviceChart(deviceId, chartViewModel)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DeviceChart(deviceId: String, chartViewModel: ChartViewModel) {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val previous10Days = currentDate.minusDays(10)

    val chart = produceState<Progress<Response<List<SingleChartItem>>, Boolean, Exception>>(
        initialValue = Progress(loading = true)
    ) {
        value = chartViewModel.viewChart(
            deviceId ="DEpMa5bnYsWaQJ25p",// deviceId.split("=")[0],//"DEQgmvSysm7q7SHA9",
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

            if (chart.loading == true) {
                //TODO loading bar
                Log.d("TAG", "Details: last device status loading")
            } else {
                LazyColumn(
                    modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
                ) {
                    items(items = chart.data?.response!!) { device ->
                        ShowSensorChart(device.data)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowSensorChart(data: List<Data>) {
    val lineData = data.map { values->
//        val time = values["time"] as String
        Log.d("TAG", "ShowSensorChart: $values")
        LineData(values.time.split(" ")[1], (values.DOOR_OPEN_STATUS.toFloat()))
    }
    Log.d("TAG", "ShowSensorChart: $lineData")
    val take10Last = lineData.takeLast(10)
    LineChart(lineData = take10Last, color = Color.Red, modifier = Modifier.fillMaxWidth().height(300.dp))
}
