package com.example.asmartprintingservice.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.IconType
import com.example.asmartprintingservice.presentation.auth.AuthEvent
import com.example.asmartprintingservice.presentation.auth.AuthState
import com.example.asmartprintingservice.presentation.auth.AuthViewModel

import com.example.asmartprintingservice.presentation.components.TopApp
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel
import com.example.asmartprintingservice.presentation.navigation.NavBarBody
import com.example.asmartprintingservice.presentation.navigation.NavBarHeader
import com.example.asmartprintingservice.presentation.navigation.NavigationItem
import com.example.asmartprintingservice.presentation.navigation.SetUpNavGraph
import com.example.asmartprintingservice.presentation.navigation.SetUpNavGraphAdmin
import com.example.asmartprintingservice.presentation.navigation.welcom.NavGraphWelcom
import com.example.asmartprintingservice.ui.theme.ASmartPrintingServiceTheme
import com.example.asmartprintingservice.ui.theme.Red
import com.example.asmartprintingservice.util.Route
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ASmartPrintingServiceTheme {
                //MainScreen()
                AdminMainScreen("1d22fa05-90f5-4430-8dd5-a75798fa5df2")
            }
        }
    }
}

@Composable
fun Welcom() {
    val navController = rememberNavController()
    NavGraphWelcom(
        navController = navController,
    )
}

@Composable
fun MainScreen(
    userId : String
) {
//    val historyDataViewModel = hiltViewModel<HistoryDataViewModel>()
//    val historyDataState = historyDataViewModel.historyDataState.collectAsStateWithLifecycle().value
//
//    val ManagePrinterDataViewModel = hiltViewModel<ManagePrinterViewModel>()
//    val ManagePrinterDataState =
//        ManagePrinterDataViewModel.printerState.collectAsStateWithLifecycle().value


    val items = listOf(
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_file_upload_24)),
            title = "Upload and Print",
            route = Route.Upload.name
        ),
        NavigationItem(
            icon = IconType.VectorIcon(Icons.Filled.ShoppingCart),
            title = "Buy pager",
            route = Route.Buying.name
        ),
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_history_24)),
            title = "History",
            route = Route.History.name
        ),
//        NavigationItem(
//            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_chat_24)),
//            title = "Chat Help",
//            route = Route.ChatHelp.name
//        ),
    )

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()
                Spacer(modifier = Modifier.height(8.dp))
                NavBarBody(items = items, currentRoute = currentRoute) { currentNavigationItem ->
                    navController.navigate(currentNavigationItem.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                            // Pop up to the start destination, clearing the back stack
                            popUpTo(startDestinationRoute) {
                                // Save the state of popped destinations
                                saveState = true
                            }
                        }

                        // Configure navigation to avoid multiple instances of the same destination
                        launchSingleTop = true

                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }


                    scope.launch {
                        drawerState.close()
                    }

                }

                Spacer(modifier = Modifier.height(80.dp))

                NavigationDrawerItem(
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_logout_24),
                                contentDescription = "Log out",
                                tint = Red
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Log out",
                                color = Red,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    },
                    selected = false,
                    onClick = {}
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopApp() {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            },
        ) { paddingValues ->
            SetUpNavGraph(navController,paddingValues, userId)
        }
    }


}

@Composable
fun AdminMainScreen(
    userId : String
)
{
    Log.d("adminMainScreen", "loadG")
    val items = listOf(
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_printshop_24)),
            title = "ManagePrinter",
            route = Route.ManagePrinter.name
        )

//        NavigationItem(
//            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_printshop_24)),
//            title = "ManagePrinter",
//            route = Route.ManagePrinter.name
//        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavBarHeader()
                Spacer(modifier = Modifier.height(8.dp))
                NavBarBody(items = items, currentRoute = currentRoute) { currentNavigationItem ->
                    navController.navigate(currentNavigationItem.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                            // Pop up to the start destination, clearing the back stack
                            popUpTo(startDestinationRoute) {
                                // Save the state of popped destinations
                                saveState = true
                            }
                        }

                        // Configure navigation to avoid multiple instances of the same destination
                        launchSingleTop = true

                        // Restore state when re-selecting a previously selected item
                        restoreState = true
                    }


                    scope.launch {
                        drawerState.close()
                    }

                }

                Spacer(modifier = Modifier.height(80.dp))

                NavigationDrawerItem(
                    label = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_logout_24),
                                contentDescription = "Log out",
                                tint = Red
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = "Log out",
                                color = Red,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    },
                    selected = false,
                    onClick = {}
                )

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopApp() {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            },
        ) { paddingValues ->
            SetUpNavGraphAdmin(navController,paddingValues, userId)
        }
    }

}