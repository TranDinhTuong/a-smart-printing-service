package com.example.asmartprintingservice.presentation.navigation

import com.example.asmartprintingservice.presentation.IconType

data class NavigationItem(
    val icon: IconType,
    val title: String,
    val route: String
)