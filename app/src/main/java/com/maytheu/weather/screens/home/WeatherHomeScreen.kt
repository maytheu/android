package com.maytheu.weather.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maytheu.weather.R
import com.maytheu.weather.data.DataOrException
import com.maytheu.weather.model.Weather
import com.maytheu.weather.model.WeatherList
import com.maytheu.weather.screens.home.WeatherHomeViewModel
import com.maytheu.weather.utils.formatDate
import com.maytheu.weather.utils.formatDateTime
import com.maytheu.weather.utils.formatDecimals
import com.maytheu.weather.widgets.WeatherAppBar

@Composable
fun WeatherHomeScreen(navController: NavController, homeViewModel: WeatherHomeViewModel) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = homeViewModel.getWeather(city = "mumbai")
    }.value

    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        WeatherData(weatherData.data!!, navController)
    }
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun WeatherData(data: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${data.city.name}, ${data.city.country}",
            elevation = 5.dp,
            isMainScreen = true,
            navController = navController,
        )
    }) {
        WeatherContent(data = data)
    }
}

@Composable
fun WeatherContent(data: Weather) {
    var weatherList = data.list[0]
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherList.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            modifier = Modifier.padding(5.dp)
        )

        //main icon
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherImageIcon(code = weatherList.weather[0].icon)
                Text(
                    text = formatDecimals(weatherList.main.temp) + "°",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherList.weather[0].main, fontStyle = FontStyle.Italic
                )
            }
        }
        HumidityWindPressure(data = weatherList)

        Divider()

//        SunsetSunrise(data = weatherList)
        Text(
            text = "Next Three Hours",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp)
        )

        Divider()

        HoursForecast(hourlyWeather = data.list)
    }

}

@Composable
fun HoursForecast(hourlyWeather:List<WeatherList>) {
    Surface(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(bottomStart = 40.dp, topStart = 40.dp, bottomEnd = 40.dp))
            .fillMaxWidth(), color = Color(0xFFFFFFFF)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Text(text = "day/time")

            WeatherImageIcon(code = "04n", 50.dp)

            Text(
                text = "Light Rain",
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color(0xFFFFC400))
            )

            Text(text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                    withStyle(
                        style = SpanStyle(
                            color = Color(0xFFFFC400),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp
                        )
                    ) {
                        append("53°")
                        withStyle(
                            style = SpanStyle(
                                color = Color(0xFF797569),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Light,
                            )
                        ) {
                            append("43°")
                        }
                    }
                }
            })
        }
    }
}

@Composable
fun SunsetSunrise(data: WeatherList) {

    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(modifier = Modifier.padding(bottom = 5.dp, top = 15.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = formatDateTime(data.sys.sunrise), style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = formatDateTime(data.sys.sunset), style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun HumidityWindPressure(data: WeatherList) {
    Row(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.main.humidity} %", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.main.pressure} psi", style = MaterialTheme.typography.caption)
        }

        Row(modifier = Modifier.padding(5.dp)) {
            Image(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(text = "${data.wind.speed} mph", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun WeatherImageIcon(code: String, size: Dp = 80.dp) {
    val imageUrl = "https://openweathermap.org/img/wn/$code.png"

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(imageUrl).build(),
        contentDescription = "icon",
        modifier = Modifier.size(size)
    )

}



