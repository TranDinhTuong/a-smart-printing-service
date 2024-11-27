package com.example.asmartprintingservice.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.asmartprintingservice.presentation.BuyingScreen
import com.example.asmartprintingservice.presentation.HistoryScreen
import com.example.asmartprintingservice.presentation.HomeScreen
import com.example.asmartprintingservice.presentation.PreviewHistoryScreen
import com.example.asmartprintingservice.presentation.PrintingScreen
import com.example.asmartprintingservice.presentation.UploadScreen
import com.example.asmartprintingservice.util.Route

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues
) {
    NavHost(navController = navController,
        startDestination = Route.HomeScreen.name){

        composable(Route.HomeScreen.name) {
            HomeScreen(navController, innerPadding)
        }

        composable(route = "${Route.Upload.name}"){
            UploadScreen(
                innerPadding
            ){
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
                PrintingScreen(innerPadding,fileId){
                    navController.navigate(Route.Buying.name)
                }
            }
        }

        composable(Route.Buying.name) {
            BuyingScreen(innerPadding)
        }

        composable(Route.History.name) {
            PreviewHistoryScreen(innerPadding)
        }
    }
}