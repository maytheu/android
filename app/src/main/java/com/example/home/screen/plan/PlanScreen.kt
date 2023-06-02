package com.example.home.screen.plan

import android.app.Activity
import android.graphics.Bitmap
import android.util.Log
import android.view.ViewGroup
import android.webkit.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.home.component.Expandable
import com.example.home.component.Layout
import com.example.home.model.Attribute
import com.example.home.model.Device
import com.example.home.model.TempKey
import com.example.home.model.UserDb
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel
import com.example.home.utils.Progress

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
            headerText = user[0].firstName,
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
        //filter devices with no position
        val devicesWitPos =
            devices?.get(0)?.devices!!.filter { dev -> dev.position.lat != "null" && dev.position.lng != "null" && dev.position.lat != null && dev.position.lng != null }
        DeviceCard(devicesWitPos, floorId = urlParams[0], planViewModel, navController)
    }

}

@Composable
fun DeviceCard(
    devices: List<Device>? = null,
    floorId: String,
    planViewModel: PlanViewModel,
    navController: NavController,
) {
    if (!devices.isNullOrEmpty()) {
        val phoneDims = LocalContext.current.resources.displayMetrics
        val sdkLoadingState = remember { mutableStateOf(false) }
        Column {
            val tempKey = produceState<Progress<TempKey, Boolean, Exception>>(
                initialValue = Progress(
                    TempKey(
                        ""
                    ), true, Exception("")
                )
            ) {
                value = planViewModel.getTempKey()
            }.value

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(phoneDims.heightPixels.dp.times(0.35f)),
                color = Color(0xFFEEF1EF),
                shape = RoundedCornerShape(20.dp),
            ) {
                if (tempKey.loading == true) {
                    Log.d("TAG", "DeviceCard: loading")
                } else {
                    LoadFpeSdk(
                        floorPlanId = floorId,
                        key = tempKey.data?.authorization!!,
                        sdkLoadingState,
                        devices,
                        navController
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

//            Surface(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(),
//                color = Color(0xFFEEF1EF),
//                shape = RoundedCornerShape(20.dp),
//            ) {
//                LazyColumn(
//                    modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
//                ) {
//                    items(items = devices) { device ->
//                        DeviceExpandableCard(device)
//                    }
//                }
//            }
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
fun ArchilogicSdk(floorId: String, key: String) {

    AndroidView(factory = { context ->
        WebView(context).apply {
            // Configure WebView settings
            settings.javaScriptEnabled = true

//            loadUrl("https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js")


// Inject JavaScript code to pass the floorId to the floor engine
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    val javascriptCode = evaluateArchilogicCode(floorId, key)
                    evaluateJavascript(javascriptCode, null)

                    // Load fpe.js script
//                    evaluateJavascript(
//                        "javascript:(function() { var script = document.createElement('script'); script.src = 'https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js'; script.onload = function() { onScriptLoaded(); }; document.head.appendChild(script); })();",
//                        null
//                    )

                    // Execute JavaScript code after the page finishes loading
//                    val jsCode = "window.floorEngine.load('$floorId');"
//                    evaluateJavascript(evaluateArchilogicCode(floorId, key), null)
                }


            }

//             // Load an empty page
//            loadData("<html></html>", "text/html", "UTF-8")

            // Load the external JavaScript SDK

            loadDataWithBaseURL(
                "https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js",
                makeHTML(),
                "text/html",
                "UTF-8",
                null
            )


        }
    })

}


private fun evaluateArchilogicCode(floorId: String, token: String, height: Int = 100): String {
    Log.d("TAG", "evaluateArchilogicCode: $floorId $token")
//    val floorCtrl = "window.floorEngine.load('$floorId');"
//
//    Log.d("TAG", "evaluateArchilogicCode: ")
//    val floor = "'$floorCtrl'.loadScene('$floorId', {authorization:'$key'})"
//    return floor

    return """
        function onScriptLoaded() {
        // Create publishable access token at https://app.archilogic.com/team/settings/access-tokens
        const publishableToken = 'd3860d6c-e26e-4934-bd5e-d2f7f707e077';
//        const height = '$height';

        const container = document.getElementById('hello-plan');
        document.getElementById('hello-plan').style.height = '$height'

        const floorPlan = new FloorPlanEngine(container);
        floorPlan.loadScene('$floorId', { authorization:'$token' });
    }
    onScriptLoaded();
    """.trimIndent()
}

class WebViewHolder {
    var webView: WebView? = null
}


private fun makeHTML(): String {
    val sb = StringBuilder()
    sb.append("<!DOCTYPE html>\n")
    sb.append("<html>\n")
    sb.append("<head>\n")
    sb.append("<script src=\"https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js\"></script>\n")
    sb.append("<style>\n")
    sb.append("</style>\n")
    sb.append("</head>\n")
    sb.append("<body>\n")
    sb.append("<div id=\"hello-plan\"></div>\n")
    sb.append("</body>\n")
    sb.append("</html>\n")
    return sb.toString()
}

@Composable
fun LoadFpeSdk(
    floorPlanId: String,
    key: String,
    sdkLoadingState: MutableState<Boolean>,
    devices: List<Device>,
    navController: NavController,
) {
    val token = key.replace("+", "PARROT")
    val context = LocalContext.current

    AndroidView(
        factory = {
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
                )

                webViewClient = WebViewClient()
                // to play video on a web view
                settings.javaScriptEnabled = true

                // to verify that the client requesting your web page is actually your Android app.
                settings.userAgentString =
                    System.getProperty("http.agent") //Dalvik/2.1.0 (Linux; U; Android 11; M2012K11I Build/RKQ1.201112.002)

//                settings.useWideViewPort = true

                // Bind JavaScript code to Android code
                addJavascriptInterface(FloorNavInterface(navController), "Android")


                webViewClient = object : WebViewClient() {
                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?,
                    ) {
                        super.onReceivedError(view, request, error)
                        Log.d("test001", "error")
                    }

                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        sdkLoadingState.value = true
//                    openFullDialogCustom.value = true
//                    backEnabled = view.canGoBack()
                        Log.d("TAG", "onPageStarted: paget started loading")
                    }

                    // Compose WebView Part 7 | Hide elements from web view
                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("TAG", "onPageFinished: page finishe")
                        super.onPageFinished(view, url)

                        val devicesPos = devices.map { device ->
                            "{ \"lat\": \"${device.position.lat}\", " + "\"lng\": \"${device.position.lng}\", " + "\"notification\": \"${device.notificationDue}\", " + " \"id\": \"${device.deviceId}\", " + "\"name\": \"${device.deviceName}\", " + "\"svg\": \"${device.sensorIcon}\" }"
                        }
                        val deviceStr = "[${devicesPos.joinToString(",")}]"

                        Log.d("TAG", "onPageFinished: $deviceStr")

                        evaluateJavascript(
                            "loadFpeSdk('$floorPlanId', '$key', '$deviceStr')", null
                        )
                        sdkLoadingState.value = false


//                    FloorPlanCallback
//                    openFullDialogCustom.value = false
//                    removeElement(view!!)
                    }


                }

                loadUrl("file:///android_asset/floor.html")


            }
        },
    )

    if (sdkLoadingState.value) {
        CircularProgressIndicator()
    }
}

//interact with js when icon clicked
class FloorNavInterface(private val navController: NavController) {
    @JavascriptInterface
    fun deviceDetailsScreen(deviceId: String) {
        val activity = navController.context as? Activity
        activity?.runOnUiThread{
            navController.navigate("${ParrotScreens.DeviceDetailsScreen.name}/deviceId/${deviceId}")
        }
    }

}

@Composable
fun onNavigateToDetailScreen(deviceId: String, navController: NavController) {

}



