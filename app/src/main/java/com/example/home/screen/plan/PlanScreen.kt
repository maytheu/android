package com.example.home.screen.plan

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
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
import com.example.home.model.TempKey
import com.example.home.model.UserDb
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
        DeviceCard(devices?.get(0)?.devices!!, floorId = urlParams[0], planViewModel)
    }

}

@Composable
fun DeviceCard(devices: List<Device>? = null, floorId: String, planViewModel: PlanViewModel) {
    if (!devices.isNullOrEmpty()) {
        Column {
            //TODO show webview
            val tempKey =
                produceState<Progress<TempKey, Boolean, Exception>>(
                    initialValue = Progress(
                        TempKey(
                            ""
                        ), true, Exception("")
                    )
                ) {
                    value = planViewModel.getTempKey()
                }.value

            Log.d("TAG", "DeviceCard: $tempKey")

            ArchilogicSdk(floorId, key = "123333")
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
fun ArchilogicSdk(floorId: String, key: String) {
    val webViewHolder = remember { WebViewHolder() }

    AndroidView(factory = { context ->
        WebView(context).apply {
            // Configure WebView settings
            settings.javaScriptEnabled = true

//            loadUrl("https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js")


// Inject JavaScript code to pass the floorId to the floor engine
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    val javascriptCode = evaluateArchilogicCode(floorId)
                    webViewHolder.webView?.evaluateJavascript(javascriptCode, null)

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
            makeHTML()?.let {
                loadDataWithBaseURL(
                    "https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js",
                    it, "text/html", "UTF-8", null
                )
            }

            // Store the WebView instance in the WebViewRef
            webViewHolder.webView = this
        }
    })

//    LaunchedEffect(floorId) {
//        val javascriptCode = evaluateArchilogicCode(floorId)
//        evaluateJavaScript(javascriptCode)
//    }
}


private fun evaluateArchilogicCode(floorId: String): String {
    Log.d("TAG", "evaluateArchilogicCode: $floorId")
//    val floorCtrl = "window.floorEngine.load('$floorId');"
//
//    Log.d("TAG", "evaluateArchilogicCode: ")
//    val floor = "'$floorCtrl'.loadScene('$floorId', {authorization:'$key'})"
//    return floor

    return """
        function onScriptLoaded() {
        // Create publishable access token at https://app.archilogic.com/team/settings/access-tokens
        const publishableToken = 'd3860d6c-e26e-4934-bd5e-d2f7f707e077';
        const demoSceneId = '$floorId';

        const container = document.createElement('div');
        container.id = 'hello-plan';
        document.body.appendChild(container);

        const floorPlan = new FloorPlanEngine(container);
        floorPlan.loadScene('$floorId', { authorization:"AL-Temp-Token 8C1eJEnKUDit1AqspNLK98eJnDWdKcWUImRY6NJfVQYOtsBsRBMJaX4s/C8rwz2lzAIRDINBF7LUOvPqcR6TGrVUF7h63x6jSwpa7W0KOFipey4+8bwFCuhAMl5mA0NADNEJFHRtXcYg8PTNn5UHKKkPCSt7LiWy6e6AcYShxjTxla2pIWfS0+dUJV8b60+PvcWdYwN/N7hpjSMyCzZOjmjQTuLI65Ypd/D8U5IuRpxPfFGV9ZnFeUlsIC9pFqapaVZeK0VM0nLIwukEB1Z0choub7KhJ2tiq/ZagmcoToHIhJ84AgY/3an5ZVSoGfHVDAfulvEQItnGO0lg76YMbBaxr0luDrLrnhQ1Ipcjkiv4rBlZ21TmxTk41v09mPsJRUnG7sHiA/kazSj7CmYaSAmK+Wn63S/S8VcrIp4l6jRsKHFDQLGMBPximKhXh6QUx/HSvdFzdLZ4+Nc75+c0oETeam6Kl4fC1dLboxLHJwPP5FsbdbkcaMD1tWBRQxhrelIdesCaDFitRf4k0kBWGL/FBbiGX7Xu3HEge3R5MkXjOaKI2xe9wxGLaahHpk+867YcAOo1YNAwE8g7lBGkRZsVS3JnCU4KYvlaCrjmkc41j6ZPel9rV/KhreqUTGy8dz2p818MA3qKtA6uAQgNTbMJ5xsTQidGw0Ex4LvWd8iH4vBop1KslZWyyg8wZ9KVjTObeE8GGcMZEoZRGkbC2g==" });
    }
    onScriptLoaded();
    """.trimIndent()
}

class WebViewHolder {
    var webView: WebView? = null
}


private fun makeHTML(): String? {
    val sb = StringBuilder()
    sb.append("<!DOCTYPE html>\n")
    sb.append("<html>\n")
    sb.append("<head>\n")
    sb.append("<script src=\"https://code.archilogic.com/fpe-sdk/v3.1.x/fpe.js\"></script>\n")
    sb.append("</head>\n")
    sb.append("<body>\n")
    sb.append("<div id=\"hello-plan\"></div>\n")
    sb.append("</body>\n")
    sb.append("</html>\n")
    return sb.toString()
}