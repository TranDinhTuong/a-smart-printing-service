package com.example.asmartprintingservice.presentation.navigation
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavHostController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.example.asmartprintingservice.presentation.ManageRequestPage
import com.example.asmartprintingservice.presentation.PreviewHistoryScreen
import com.example.asmartprintingservice.presentation.PreviewManageRequestPage

import com.example.asmartprintingservice.presentation.PrintingScreen
import com.example.asmartprintingservice.presentation.Settings
import com.example.asmartprintingservice.presentation.UploadScreen
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel
import com.example.asmartprintingservice.presentation.manageRequest.ManageRequestViewModel
import com.example.asmartprintingservice.presentation.settings.SettingsViewModel
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

        composable(Route.ManageRequest.name) {
            Log.d("ManageRequestPage", "how did we get here")
            PreviewManageRequestPage(innerPadding,userId)
        }

        composable(Route.Seeting.name) {
            val viewModel = hiltViewModel<SettingsViewModel>()
            val state = viewModel.settingsState.collectAsStateWithLifecycle().value
            Settings(
                state = state,
                onEvent = viewModel::onEvent,
                viewModel = viewModel
            )
        }
    }
}