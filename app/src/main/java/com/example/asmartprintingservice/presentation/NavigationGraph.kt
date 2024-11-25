package com.example.composetutorial.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavigationGraph(startDestination: String = "home") {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable("home") {
            HomePageScreen(
                onStudentClick = {
                    navController.navigate("student_login")
                },
                onAdminClick = {
                    navController.navigate("admin_login")
                }
            )
        }
        composable("student_login") {
            LoginAsStudentScreen() // Student Login Screen
        }
        composable("admin_login") {
            LoginAsAdminScreen() // Admin Login Screen
        }
    }
}
