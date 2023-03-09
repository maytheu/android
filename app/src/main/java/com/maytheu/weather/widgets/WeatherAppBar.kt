package com.maytheu.weather.widgets

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maytheu.weather.model.Favourites
import com.maytheu.weather.navigation.WeatherScreens
import com.maytheu.weather.screens.favourites.WeatherFavouritesViewModel

@Composable
fun WeatherAppBar(
    title: String = "Lagos",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    weatherFavouritesViewModel: WeatherFavouritesViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
) {
    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowMenuDialog(showDialog = showDialog, navController = navController)
    }

    val city = title.split(",")
    weatherFavouritesViewModel.getFavouriteCity(city = city[0])

    TopAppBar(
        title = {
            Text(
                text = title, style = TextStyle(
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colors.onSecondary
            )
        },
        actions = {
            if (isMainScreen) {
                IconButton(onClick = { onAddActionClicked.invoke() }) {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                }

                IconButton(onClick = { showDialog.value = true }) {
                    Icon(imageVector = Icons.Rounded.MoreVert, contentDescription = "more")
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = "back",
                    tint = MaterialTheme.colors.onSecondary,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    }
                )
            }
            if (isMainScreen) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "favourite",
                    tint = if (weatherFavouritesViewModel.checkFavouriteCity.value) Color.Red.copy(
                        alpha = 0.9f
                    ) else Color.LightGray,
                    modifier =
                    Modifier
                        .scale(0.9f)
                        .clickable {
                            if (!weatherFavouritesViewModel.checkFavouriteCity.value) {
                                weatherFavouritesViewModel.addFavouriteCity(
                                    Favourites(
                                        city = city[0],
                                        country = city[1]
                                    )
                                )
                            } else {
                                weatherFavouritesViewModel.deleteFavouriteCity(
                                    Favourites(
                                        city = city[0],
                                        country = city[1]
                                    )
                                )
                            }
                        }
                )
            }
        },
        backgroundColor = Color.Transparent,
        elevation = elevation
    )

}

@Composable
fun ShowMenuDialog(showDialog: MutableState<Boolean>, navController: NavController) {
    var expanded by remember {
        mutableStateOf(true)
    }
    val menuItems = listOf("About", "Favourites", "Settings")
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false },
            modifier = Modifier
//                .width(140.dp)
                .background(Color.White)
        ) {
            menuItems.forEachIndexed { index, menu ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    showDialog.value = false
                }) {
                    Icon(
                        imageVector = when (menu) {
                            "About" -> Icons.Default.Info
                            "Favourites",
                            -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        }, contentDescription = menu,
                        tint = Color.LightGray
                    )
                    Text(
                        text = menu,
                        modifier = Modifier.clickable {
                            navController.navigate(
                                when (menu) {
                                    "About" -> WeatherScreens.AboutScreen.name
                                    "Favourites" -> WeatherScreens.FavouriteScreen.name
                                    else -> WeatherScreens.SettingScreen.name
                                }
                            )
                        },
                        fontWeight = FontWeight.W300
                    )
                }
            }
        }
    }
}
