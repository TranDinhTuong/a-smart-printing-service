package com.example.asmartprintingservice.presentation.navigation.welcom

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.asmartprintingservice.presentation.BuyingScreen
import com.example.asmartprintingservice.presentation.HomeScreen
import com.example.asmartprintingservice.presentation.LoginAsStudentScreen
import com.example.asmartprintingservice.presentation.PreviewHistoryScreen
import com.example.asmartprintingservice.presentation.PrintingScreen
import com.example.asmartprintingservice.presentation.SelectRoleScreen
import com.example.asmartprintingservice.presentation.UploadScreen
import com.example.asmartprintingservice.presentation.components.HomePageScreen
import com.example.asmartprintingservice.presentation.components.SelectRole
import com.example.asmartprintingservice.ui.MainScreen
import com.example.asmartprintingservice.util.Route

@Composable
fun NavGraphWelcom(
    navController: NavHostController,
) {
    NavHost(navController = navController,
        startDestination = Route.welcome.name){

        composable(Route.welcome.name) {
            HomePageScreen(){
                navController.navigate(Route.login.name)
            }
        }

//        composable(Route.slectRole.name) {
//            SelectRoleScreen(){
//                navController.navigate(Route.login.name)
//            }
//        }

        composable(Route.login.name) {
            LoginAsStudentScreen(){user ->
                user?.let {
                    navController.navigate("${Route.MainScreen.name}/{${it.id}}")
                }
            }
        }

        composable(
            route = "${Route.MainScreen.name}/{userId}",
            arguments = listOf(
                navArgument(name = "userId"){
                    type = NavType.StringType
                }
            )
        ) {backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            userId?.let {
                MainScreen(it)
            }
        }
    }
}