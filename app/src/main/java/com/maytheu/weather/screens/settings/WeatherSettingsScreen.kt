package com.maytheu.weather.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.maytheu.weather.model.Setting
import com.maytheu.weather.screens.settings.WeatherSettingsViewModel
import com.maytheu.weather.widgets.WeatherAppBar

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherSettingsScreen(
    navController: NavController,
    settingsViewModel: WeatherSettingsViewModel = hiltViewModel(),
) {

    val meaasurementUnit = listOf<String>("Imperial (F)", "Metric (F)")

    val choicesFromDb = settingsViewModel.units.collectAsState().value

    val defaultChoice =
        if (choicesFromDb.isNullOrEmpty()) meaasurementUnit[0] else choicesFromDb[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }

    var unitToggleState by remember {
        mutableStateOf(defaultChoice === meaasurementUnit[0])
    }

    Scaffold(topBar = {
        WeatherAppBar(
            navController = navController,
            title = "Settings",
            isMainScreen = false,
            icon = Icons.Default.ArrowBack
        ) {
            navController.popBackStack()
        }
    }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Change units of measurement",
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                IconToggleButton(
                    checked = !unitToggleState,
                    onCheckedChange = {
                        unitToggleState = !it
                        if (unitToggleState) {
                            choiceState = meaasurementUnit[0]
                        } else {
                            choiceState = meaasurementUnit[1]
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f)),
                ) {
                    Text(text = if (unitToggleState) "Fahrenheit °F" else "Celcius °C")
                }
                Button(
                    onClick = {
                        settingsViewModel.deleteAll()
                        settingsViewModel.addUnit(Setting(unit = choiceState))
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape((34.dp)),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(
                        text = "Save",
                        modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }

        }
    }
}