package com.example.home.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.model.Floor
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.floor.FloorViewModel
import com.example.home.screen.home.HomeViewModel

@Composable
fun FloorScreen(
    navController: NavController,
    assetId: String,
    homeViewModel: HomeViewModel,
    floorViewModel: FloorViewModel,
) {
    val user = homeViewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = user[0].firstName,
            navController = navController,
            back = true,
            imageUrl = user[0].logo
        ) {
            AssetFloors(assetId, companyId = user[0].userCompanyId, floorViewModel, navController)
        }
    }
}

@Composable
fun AssetFloors(
    assetId: String,
    companyId: String,
    floorViewModel: FloorViewModel,
    navController: NavController,
) {
    floorViewModel.loadAssetDevices(assetId, companyId)

    if (floorViewModel.devices.value.loading == true) {
        Log.d("TAG", "AssetFloors: loading")
    } else if (!floorViewModel.devices.value.data?.response.isNullOrEmpty()) {
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
                items(items = floorViewModel.devices.value.data?.response!!) { floor ->
                    FloorCard(floor, navController)
                }
            }
        }
    } else {
        Box {}
    }


}

@Composable
fun FloorCard(floor: Floor, navController: NavController) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("${ParrotScreens.PlanScreen.name}/floorId/${floor.floorPlanId}") },
        shape = CircleShape.copy(topEnd = CornerSize(10.dp)),
        color = Color.White, elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Text(text = floor.floorNumber.toString())
            Text(
                text = floor.floorName,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(start = 10.dp),
                style = MaterialTheme.typography.subtitle2

            )
        }

    }
}


