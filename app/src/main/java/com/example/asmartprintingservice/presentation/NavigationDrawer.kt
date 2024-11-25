package com.example.composetutorial.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Arrangement
import com.example.composetutorial.R

@Composable
fun NavigationDrawer() {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var isChecked3 by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Menu",
                    modifier = Modifier.padding(16.dp),
                    color = Color.Black,
                    style = MaterialTheme.typography.headlineMedium
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = if (isChecked1) Color.Yellow else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    IconButton(
                        onClick = {
                            isChecked1 = true
                            isChecked2 = false
                            isChecked3 = false
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        val iconTint = if (isChecked1) Color.Black else Color.Gray
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.home_24px),
                                contentDescription = "Manage User Icon",
                                tint = iconTint,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "Home",
                                style = MaterialTheme.typography.headlineSmall,
                                color = iconTint
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = if (isChecked2) Color.Yellow else Color.Transparent,
                            shape = MaterialTheme.shapes.medium // Bo góc
                        )
                ) {
                    IconButton(
                        onClick = {
                            isChecked1 = false
                            isChecked2 = true
                            isChecked3 = false
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        val iconTint = if (isChecked2) Color.Black else Color.Gray
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.print_connect_24px),
                                contentDescription = "Manage Printers Icon",
                                tint = iconTint,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "Manage Printers",
                                style = MaterialTheme.typography.headlineSmall,
                                color = iconTint
                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = if (isChecked3) Color.Yellow else Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    IconButton(
                        onClick = {
                            isChecked1 = false
                            isChecked2 = false
                            isChecked3 = true
                            scope.launch { drawerState.close() }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                    ) {
                        val iconTint = if (isChecked3) Color.Black else Color.Gray
                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.person_24px),
                                contentDescription = "Manage User Icon",
                                tint = iconTint,
                                modifier = Modifier.size(30.dp)
                            )
                            Text(
                                text = "Manage User",
                                style = MaterialTheme.typography.headlineSmall,
                                color = iconTint
                            )
                        }
                    }
                }
            }
        }
    ) {
        Scaffold { paddingValues ->
            Home_page_of_admin(
                modifier = Modifier.padding(paddingValues),
                onClickMenu = {
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            )
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewNavigationDrawer1() {
    NavigationDrawer()
}