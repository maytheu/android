package com.maytheu.weather.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.maytheu.weather.R
import com.maytheu.weather.model.Weather
import com.maytheu.weather.model.WeatherList
import com.maytheu.weather.utils.formatDate
import com.maytheu.weather.utils.formatDateTime
import com.maytheu.weather.utils.formatDecimals


@Composable
fun WeatherContent(data: Weather, isImperial: Boolean) {
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

        HumidityWindPressure(data = weatherList, isImperial = isImperial)

        Divider()

//        SunsetSunrise(data = weatherList)

        Text(
            text = "Forecast in view",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(top = 7.dp, bottom = 7.dp)
        )

        Divider()

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(20.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)) {
                items(items = data.list) { item ->
                    //show weather hourly forecast list
                    WeatherDetail(weather = item)
                }
            }
        }

        //attempt
//        HoursForecast(hourlyWeather = data.list)
    }

}

@Composable
fun WeatherDetail(weather: WeatherList) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(10.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${formatDate(weather.dt).split(",")[0]}, ${formatDateTime(weather.dt)}",
                modifier = Modifier.padding(start = 5.dp)
            )

            WeatherImageIcon(code = weather.weather[0].icon)

            Surface(
                modifier = Modifier.padding(2.dp), shape = CircleShape, color = Color(0xFFFFC400)
            ) {
                Text(
                    text = weather.weather[0].description,
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.caption
                )
            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(weather.main.temp_max) + "°")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray
                    )
                ) {
                    append(formatDecimals(weather.main.temp_min) + "°")
                }
            })

        }
    }
}

@Composable
fun HoursForecast(hourlyWeather: List<WeatherList>) {
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
fun HumidityWindPressure(data: WeatherList, isImperial: Boolean) {
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
            Text(
                text = "${formatDecimals(data.wind.speed)}" + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )
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
