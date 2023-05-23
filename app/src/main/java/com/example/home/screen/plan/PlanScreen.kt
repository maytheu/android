package com.example.home.screen.plan

import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.home.component.Expandable
import com.example.home.component.Layout
import com.example.home.model.Attribute
import com.example.home.model.Device
import com.example.home.model.UserDb
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel

@Composable
fun PlanScreen(
    navController: NavController,
    planViewModel: PlanViewModel,
    floorViewModel: FloorViewModel,
    homeViewModel: HomeViewModel,
    floorId: String,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = "${user[0].firstName}",
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            ShowDevicesOnFloor(floorId, floorViewModel, planViewModel, navController, user[0])
        }
    }
}

@Composable
fun ShowDevicesOnFloor(
    floorId: String,
    floorViewModel: FloorViewModel,
    planViewModel: PlanViewModel,
    navController: NavController,
    user: UserDb,
) {
    val urlParams = floorId.split("=")
    floorViewModel.loadAssetDevices(urlParams[1], companyId = user.userCompanyId)

    if (floorViewModel.devices.value.loading == true) {
        Log.d("plan", "ShowDevicesOnFloor: loading")
    } else {
        val devices =
            floorViewModel.devices.value.data?.response?.filter { d -> d.floorPlanId == urlParams[0] }
        DeviceCard(devices?.get(0)?.devices!!)
    }

}

@Composable
fun DeviceCard(devices: List<Device>? = null) {
    if (!devices.isNullOrEmpty()) {
        Column {
            //TODO show webview
            ArchilogicSdk()
            Spacer(modifier = Modifier.height(25.dp))
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                color = Color(0xFFEEF1EF),
                shape = RoundedCornerShape(20.dp),
            ) {
                LazyColumn(
                    modifier = Modifier.padding(2.dp),
                    contentPadding = PaddingValues(1.dp)
                ) {
                    items(items = devices) { device ->
                        DeviceExpandableCard(device)
                    }
                }
            }
        }
    }

}

@Composable
fun DeviceExpandableCard(device: Device) {
    val phoneDims = LocalContext.current.resources.displayMetrics

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = Color(0xFFEEF1EF),
        shape = RoundedCornerShape(20.dp),
    ) {
        Expandable(expanded = false, title = device.deviceName) {
            Surface(
                modifier = Modifier
                    .height(phoneDims.heightPixels.dp.times(0.25f))
                    .fillMaxWidth()
            ) {
                LazyColumn(modifier = Modifier.padding(5.dp)) {
                    items(items = device.attributes) { att -> AttributeCard(att) }
                }
            }
        }
    }

}

@Composable
fun AttributeCard(att: Attribute) {
    val deviceAttr = att.attribute
    val deviceValue = att.attributeValue

    Row(modifier = Modifier.padding(5.dp)) {
        Text(
            text = deviceAttr,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(3f)
        )
        Text(text = deviceValue, modifier = Modifier.weight(2f))
    }

}

@Composable
fun ArchilogicSdk() {
    AndroidView(factory = { context ->
        WebView(context).apply {
            // Configure WebView settings
            settings.javaScriptEnabled = true

            // Load the external JavaScript SDK
            loadUrl("https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js")
        }
    })
}
