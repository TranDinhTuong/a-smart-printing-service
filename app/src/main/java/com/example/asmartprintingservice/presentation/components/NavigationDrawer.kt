package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.IconType
import com.example.asmartprintingservice.presentation.ItemService
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Red
import com.example.asmartprintingservice.ui.theme.Yellow
import com.example.asmartprintingservice.util.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun NavigationDrawer(
    onClickItem: @Composable (PaddingValues) -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    var isSelected by rememberSaveable {
        mutableStateOf(false)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    "Drawer title",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )

                Item(
                    icon = IconType.VectorIcon(Icons.Filled.Home),
                    text = "Homepage",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )

                Item(
                    icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_file_upload_24)),
                    text = "Upload files",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )
                Item(
                    icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_printshop_24)),
                    text = "Print files",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )

                Item(
                    icon = IconType.VectorIcon(Icons.Filled.ShoppingCart),
                    text = "Buy pager",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )
                Item(
                    icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_history_24)),
                    text = "History",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )

                Item(
                    icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_chat_24)),
                    text = "Chat Help",
                    isSelected = isSelected,
                    onClickItem = {
                        isSelected = !isSelected
                    }
                )

                Spacer(modifier = Modifier.height(80.dp))

                NavigationDrawerItem(
                    label = {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                        ){
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
    ){
        Scaffold(
            topBar = {
                TopApp(){
                    scope.launch {
                        drawerState.apply {
                            if (isClosed) open() else close()
                        }
                    }
                }
            },
        ) {paddingValues ->
            onClickItem(paddingValues)
        }
    }
}



@Composable
fun Item(
    icon: IconType,
    text: String,
    onClickItem:() -> Unit,
    isSelected : Boolean = false
) {

    NavigationDrawerItem(
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Yellow,
            selectedTextColor = Blue
        ),
        label = {
            Row (
                verticalAlignment = Alignment.CenterVertically,
            ){
                when (icon) {
                    is IconType.VectorIcon -> {
                        Icon(
                            imageVector = icon.imageVector,
                            contentDescription = text
                        )
                    }
                    is IconType.PainterIcon -> {
                        Icon(
                            painter = icon.painter,
                            contentDescription = text
                        )
                    }
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        },
        selected = isSelected,
        onClick = onClickItem
    )
}

class PaddingValuesProvider : PreviewParameterProvider<PaddingValues> {
    override val values = sequenceOf(PaddingValues())
}
@Preview(showBackground = true)
@Composable
fun PreviewNavigationDrawer(
    @PreviewParameter(PaddingValuesProvider::class) paddingValues: PaddingValues
) {
    MaterialTheme {
        NavigationDrawer(
            onClickItem = { println("Clicked item with padding: $paddingValues") }
        )
    }
}