package com.example.asmartprintingservice.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.presentation.BuyingScreen
import com.example.asmartprintingservice.presentation.HistoryScreen

import com.example.asmartprintingservice.presentation.HomeScreen
import com.example.asmartprintingservice.presentation.ManagePrinterPage

import com.example.asmartprintingservice.presentation.PrintingScreen
import com.example.asmartprintingservice.presentation.Upload
import com.example.asmartprintingservice.presentation.file.FileViewModel
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel
import com.example.asmartprintingservice.ui.theme.ASmartPrintingServiceTheme
import com.example.asmartprintingservice.util.Route
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ASmartPrintingServiceTheme {
                  MainScreen()
            }
        }
    }
}


@Preview(
    showBackground = true,
    device = Devices.PIXEL_5
)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val historyDataViewModel = hiltViewModel<HistoryDataViewModel>()
    val historyDataState = historyDataViewModel.historyDataState.collectAsStateWithLifecycle().value

    val ManagePrinterDataViewModel = hiltViewModel<ManagePrinterViewModel>()
    val ManagePrinterDataState = ManagePrinterDataViewModel.printerState.collectAsStateWithLifecycle().value

    NavHost(
        navController = navController,
        startDestination = Route.Upload.name,
    ) {
        composable(Route.HomeScreen.name) {
            HomeScreen(navController)
        }

        composable(route = "${Route.Upload.name}"){
            Upload(){
                it?.let {
                    navController.navigate("${Route.Printing.name}/$it")
                }
            }
        }

        composable(
            route = "${Route.Printing.name}/{fileId}",
            arguments = listOf(
                navArgument(name = "fileId"){
                    type = NavType.IntType
                }
            )
        ) {backStackEntry->
            val fileId = backStackEntry.arguments?.getInt("fileId")
            fileId?.let {
                PrintingScreen(fileId, historyDataState, historyDataViewModel::onEvent)
            }
        }

        composable(Route.Buying.name) {
            BuyingScreen()
        }

        composable(Route.History.name) {
            HistoryScreen(historyDataState, onEvent = historyDataViewModel::onEvent)
        }

        composable(Route.ManagePrinter.name) {
            ManagePrinterPage(ManagePrinterDataState, onEvent = ManagePrinterDataViewModel::onEvent)
        }
    }
}

