package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.R

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
)

@Preview(showBackground = true)
@Composable
fun BottomApp() {

    val items = listOf(
        BottomNavigationItem(
            title =  "Lịch SỬ IN",
            selectedIcon = painterResource(id = R.drawable.history_24dp),
            unselectedIcon = painterResource(id = R.drawable.history_24dp),
        ),
        BottomNavigationItem(
            title = "Tải TÀI LIỆU",
            selectedIcon = painterResource(id = R.drawable.download_24dp),
            unselectedIcon = painterResource(id = R.drawable.download_24dp),
        ),
        BottomNavigationItem(
            title = "IN TÀI LIỆU",
            selectedIcon = painterResource(id = R.drawable.print_24dp),
            unselectedIcon = painterResource(id = R.drawable.print_24dp)
        ),
        BottomNavigationItem(
            title = "Mua Giấy IN",
            selectedIcon = painterResource(id = R.drawable.shopping_cart_24dp),
            unselectedIcon = painterResource(id = R.drawable.shopping_cart_24dp)
        )
    )


    var selectedItem = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem.intValue == index,
                onClick = {
                    selectedItem.intValue = index
                },
                label = {
                    Text(
                        text = if(index == selectedItem.intValue) item.title else "",
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                icon = {
                    Icon(
                        painter = if (index == selectedItem.intValue) {
                            item.selectedIcon
                        } else item.unselectedIcon,
                        contentDescription = item.title,
                        modifier = if(index == selectedItem.intValue) Modifier else Modifier.alpha(0.7f)
                    )
                },
                alwaysShowLabel = true,
            )
        }
    }
}