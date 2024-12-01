package com.example.asmartprintingservice.presentation.navigation
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.asmartprintingservice.presentation.AdminHomeScreen
import com.example.asmartprintingservice.presentation.BuyingScreen
import com.example.asmartprintingservice.presentation.HomeScreen
import com.example.asmartprintingservice.presentation.ManagePrinterPage
import com.example.asmartprintingservice.presentation.PreviewHistoryScreen

import com.example.asmartprintingservice.presentation.PrintingScreen
import com.example.asmartprintingservice.presentation.UploadScreen
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel
import com.example.asmartprintingservice.util.Route

@Composable
fun SetUpNavGraphAdmin(
    navController: NavHostController,
    innerPadding: PaddingValues,
    userId : String
){
    NavHost(navController = navController,
        startDestination = Route.AdminHomeScreen.name){

        composable(Route.AdminHomeScreen.name) {
            AdminHomeScreen(navController, innerPadding)
        }

        composable(Route.ManagePrinter.name) {
            val managePrinterViewModel = hiltViewModel<ManagePrinterViewModel>()
            val managePrinterState = managePrinterViewModel.printerState.collectAsStateWithLifecycle().value
            ManagePrinterPage(
                managePrinterState = managePrinterState,
                onEvent = managePrinterViewModel::onEvent ,
                viewModel = managePrinterViewModel
            )
        }
    }
}