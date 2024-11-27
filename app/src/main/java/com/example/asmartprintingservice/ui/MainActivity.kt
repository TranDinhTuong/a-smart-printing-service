package com.example.asmartprintingservice.ui

import android.os.Bundle
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

import com.example.asmartprintingservice.presentation.components.TopApp
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel
import com.example.asmartprintingservice.presentation.printing.PrintingState
import com.example.asmartprintingservice.presentation.printing.PrintingViewModel
import com.example.asmartprintingservice.presentation.navigation.NavBarBody
import com.example.asmartprintingservice.presentation.navigation.NavBarHeader
import com.example.asmartprintingservice.presentation.navigation.NavigationItem
import com.example.asmartprintingservice.presentation.navigation.SetUpNavGraph

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

    val historyDataViewModel = hiltViewModel<HistoryDataViewModel>()
    val historyDataState = historyDataViewModel.historyDataState.collectAsStateWithLifecycle().value

    val printingViewModel = hiltViewModel<PrintingViewModel>()
    val printingState = printingViewModel.printingState.collectAsStateWithLifecycle().value


    val ManagePrinterDataViewModel = hiltViewModel<ManagePrinterViewModel>()
    val ManagePrinterDataState =
        ManagePrinterDataViewModel.printerState.collectAsStateWithLifecycle().value

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
        NavigationItem(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_chat_24)),
            title = "Chat Help",
            route = Route.ChatHelp.name
        ),
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
            SetUpNavGraph(navController,paddingValues)
        }
    }
}

