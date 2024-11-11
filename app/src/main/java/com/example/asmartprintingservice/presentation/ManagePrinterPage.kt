package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.components.AddPrinterDialog
import com.example.asmartprintingservice.presentation.components.SearchBar

@Preview(showBackground = true)
@Composable
fun ManagePrinterPage(modifier: Modifier = Modifier) {
    var sampleData = mutableListOf<PrinterData>()

    for(i in 1..10){
        sampleData.add(
            PrinterData(
                i,
                "3:40",
                "Máy in Laser Trắng Đen HP 107a (4ZB77A)",
                "In 1 mặt",
                "100-500 trang/tháng",
                "A4, B5, A5",
                "1 x USB 2.0",
                "20 trang/phút",
                "bk_cs2, Tòa: H2, phòng: 101",
                true,
                false
            )
        )
    }

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    AddPrinterDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = {
            isEditSubjectDialogOpen = false
        }
    )

    Column(
        Modifier.fillMaxSize()
    ) {
        //SearchBar()
        Spacer(modifier = Modifier.height(10.dp))
        Box(){
            GridList(items = sampleData)
            Example (
                onClick = {
                    isEditSubjectDialogOpen = true
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun GridList(items: List<PrinterData>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2 cột
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            GridItem(item = items[index])
        }
    }
}

@Composable
fun GridItem(
    item: PrinterData
) {
    var isChecked by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
        ) {
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.printer),
                    contentDescription = null,
                    Modifier.size(100.dp, 100.dp)
                )
            }
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Chức năng: ")
                    }
                    append(item.features) // Phần text thông thường
                },
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Công suất: ")
                    }
                    append(item.recommended) // Phần text thông thường
                },
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Giấy in: ")
                    }
                    append(item.paperSupport) // Phần text thông thường
                },
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Kết nối: ")
                    }
                    append(item.connectivity) // Phần text thông thường
                },
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("Địa điểm: ")
                    }
                    append(item.location) // Phần text thông thường
                },
                modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
            )
            Box (
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CustomSwitch(isChecked = isChecked) {
                    isChecked = true
                }
            }


        }
    }
}
@Composable
fun Example(
    onClick: () -> Unit,
    modifier : Modifier
) {
    FloatingActionButton(
        onClick = { onClick() },
        modifier = modifier,
        containerColor = Color(0xFF1689DC)
    ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}
@Composable
fun CustomSwitch(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,         // Màu của dấu tick (vòng tròn)
            checkedTrackColor = Color.Green,         // Màu nền khi switch bật
            uncheckedThumbColor = Color.White,       // Màu vòng tròn khi không bật
            uncheckedTrackColor = Color.Gray         // Màu nền khi switch tắt
        )
    )
}

// Dữ liệu mẫu
data class PrinterData(
    val id: Int,
    val imageUrl: String ?= null,
    val productName: String,
    val features: String,
    val recommended: String,
    val paperSupport: String,
    val connectivity: String,
    val speed: String,
    val location: String,
    val status : Boolean,
    val deleteStatus: Boolean
)