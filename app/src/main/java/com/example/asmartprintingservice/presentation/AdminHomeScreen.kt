package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.navigation.NavigationItem
import com.example.asmartprintingservice.util.Route

@Composable
fun AdminHomeScreen(
    navController: NavController,
    innerPadding: PaddingValues
){
    val items = listOf(
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_printshop_24)),
            title = "Printer",
            route = Route.ManagePrinter.name
        ),
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_send_24)),
            title = "Request",
            route = Route.ManageRequest.name
        ),
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_apps_24)),
            title = "Setting",
            route = Route.Seeting.name
        ),
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_account_circle_24)),
            title = "UserData",
            route = Route.UserData.name
        ),
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_add_chart_24)),
            title = "PrinterData",
            route = Route.PrinterData.name
        )

    )

    Spacer(modifier = Modifier.height(20.dp))
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        items(items) { item ->
            ItemService(
                icon = item.icon,
                text = item.title,
                onClickItem = {
                    if(item.route == Route.ManagePrinter.name){
                        navController.navigate(Route.ManagePrinter.name)
                    }else if(item.route == Route.ManageRequest.name)
                    {
                        navController.navigate(Route.ManageRequest.name)
                    }
                    else if(item.route == Route.Seeting.name)
                    {
                        navController.navigate(Route.Seeting.name)
                    }
                    else if(item.route == Route.UserData.name)
                    {
                        navController.navigate(Route.UserData.name)
                    }
                    else if(item.route == Route.PrinterData.name)
                    {
                        navController.navigate(Route.PrinterData.name)
                    }
                }
            )
        }
    }
}