package com.example.home.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.model.Asset
import com.example.home.model.Response
import com.example.home.model.UserDb
import com.example.home.navigation.ParrotScreens
import com.example.home.screen.home.HomeViewModel
import com.example.home.utils.Progress

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val user = viewModel.users.collectAsState().value

    if (user.isNotEmpty()) {
        Layout(
            headerText = "${user[0].firstName}",
            navController = navController,
            back = false,
            imageUrl = user[0].logo
        ) {
            CompanyAssets(user = user[0], viewModel, navController)
        }
    }
}

@Composable
fun CompanyAssets(user: UserDb, viewModel: HomeViewModel, navController: NavController) {
    val assets = produceState<Progress<Response<List<Asset>>, Boolean, Exception>>(
        initialValue = Progress(
            loading = true
        )
    ) {
        value = viewModel.loadCompanyAsset(companyId = user.userCompanyId)
    }.value

    if (assets.loading == true) {
        Log.d("loading", "CompanyAssets: show progress")
    } else {
        if (assets.data != null) {
            val buildings = assets.data?.response?.filter { asset ->
                asset.assetType == "Building"
            }

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
                    items(items = buildings!!) { building ->
                        AssetCard(building, navController)
                    }
                }
            }

        }
    }
}

@Composable
fun AssetCard(
    building: Asset, navController: NavController,
) {

    Surface(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clickable { navController.navigate("${ParrotScreens.FloorScreen.name}/assetId/${building.assetId}") },
        shape = CircleShape.copy(topEnd = CornerSize(10.dp)),
        color = Color.White, elevation = 5.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 5.dp)
                ) {
                    Icon(imageVector = Icons.Default.Badge, contentDescription = "name")
                    Text(
                        text = building.assetName,
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "name")
                    Text(text = building.location, modifier = Modifier.padding(start = 10.dp))
                }
            }

            Spacer(modifier = Modifier.width(100.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(imageVector = Icons.Default.Description, contentDescription = "name")
                Text(
                    text = building.assetDesc,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.subtitle2

                )
            }

        }
    }
}

