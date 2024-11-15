package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApp(
    onClickMenu: () -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Blue,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                "SwiftPrint",
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onClickMenu) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    tint = Yellow,
                    contentDescription = "Menu"
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "account",
                    tint = Yellow
                )
            }

            IconButton(
                onClick = {
                }
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(56.dp)
                ) {

                    Canvas(
                        modifier = Modifier.matchParentSize()
                    ) {
                        drawCircle(color = Color.White, radius = size.minDimension / 2)
                    }

                    Image(
                        painter = painterResource(id = R.drawable.logobk),
                        contentDescription = null,
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )

}