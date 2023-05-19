package com.example.home.screen

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.produceState
import androidx.navigation.NavController
import com.example.home.component.Layout
import com.example.home.model.Asset
import com.example.home.model.Response
import com.example.home.model.UserDb
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
            CompanyAssets(user = user[0], viewModel)
        }
    }
}

@Composable
fun CompanyAssets(user: UserDb, viewModel: HomeViewModel) {
    val assets =
        produceState<Progress<Response<List<Asset>>, Boolean, Exception>>(
            initialValue = Progress(
                loading = true
            )
        ) {
            value = viewModel.loadCompanyAsset(companyId = user.userCompanyId)
        }.value

    if (assets.data?.response.toString().isNotEmpty()) {
        val buildings = assets.data?.response?.filter { asset ->
            asset.assetName === "Building"

        }
        //TODO Lazy column
//        AssetCard(assets=buildings)
    }
    Log.d("Homesc", "CompanyAssets: ${assets.data?.response} ")
}

