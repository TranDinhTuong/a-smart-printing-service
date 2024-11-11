package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRailItemColors
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.asmartprintingservice.presentation.components.TopApp
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow
import kotlinx.coroutines.launch
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.ui.theme.Red
import com.example.asmartprintingservice.util.Route

@Composable
fun HomeScreen(navController: NavController) {

    val items = listOf(
        Service(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_file_upload_24)),
            text = "Upload files",
            nameService = Route.Upload
        ),
        Service(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_printshop_24)),
            text = "Print files",
            nameService = Route.Printing
        ),
        Service(
            icon = IconType.VectorIcon(Icons.Filled.ShoppingCart),
            text = "Buy pager",
            nameService = Route.Buying
        ),
        Service(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_history_24)),
            text = "History",
            nameService = Route.History
        ),
        Service(
            icon = IconType.PainterIcon(painterResource(id = R.drawable.baseline_chat_24)),
            text = "Chat Help",
            nameService = Route.ChatHelp
        ),
    )

    NavigationDrawer {
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(it)
        ) {
            items(items) { item ->
                ItemService(
                    icon = item.icon,
                    text = item.text,
                    onClickItem = {
                        if(item.nameService == Route.Upload){
                            navController.navigate(Route.Upload.name)
                        }else if(item.nameService == Route.Printing){
                            navController.navigate(Route.Printing.name)
                        }else if(item.nameService == Route.Buying){
                            navController.navigate(Route.Buying.name)
                        }else if(item.nameService == Route.History){
                            navController.navigate(Route.History.name)
                        }else if(item.nameService == Route.ChatHelp){
                            navController.navigate(Route.ChatHelp.name)
                        }
                    }
                )
            }
        }
    }

}
data class Service(
    val icon: IconType,
    val text: String,
    val nameService: Route
)
sealed class IconType {
    data class VectorIcon(val imageVector: ImageVector) : IconType()
    data class PainterIcon(val painter: Painter) : IconType()
}

@Composable
fun ItemService(
    icon: IconType ,
    text: String ,
    onClickItem: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = onClickItem,
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.size(145.dp),
            colors = ButtonDefaults.buttonColors(Yellow)
        ) {
            when(icon){
                is IconType.PainterIcon -> {
                    Icon(
                        painter = icon.painter,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Blue
                    )
                }
                is IconType.VectorIcon -> {
                    Icon(
                        imageVector = icon.imageVector,
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = Blue
                    )
                }
            }
        }
    }
    
}

