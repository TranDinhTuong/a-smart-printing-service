package com.example.asmartprintingservice.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.components.AddPrinterDialog
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.presentation.components.SearchBar
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
    val deleteStatus: Boolean,
    val isChecked: Boolean
)
@Preview(showBackground = true)
@Composable
fun ManagePrinterPage(modifier: Modifier = Modifier) {
    var sampleData by rememberSaveable {
        mutableStateOf(
            listOf(
                PrinterData(
                    1,
                    "9:00",
                    "Máy in Laser Trắng Đen Canon LBP2900",
                    "In 2 mặt",
                    "200-800 trang/tháng",
                    paperSupport = "A4, A5, Letter",
                    connectivity = "USB 2.0",
                    speed = "12 trang/phút",
                    location = "Tòa: B1, phòng: 202",
                    status = false,
                    deleteStatus = false,
                    isChecked = false
                ),
                PrinterData(
                    id = 2,
                    "10:15",
                    productName = "Máy in Màu Brother HL-L3230CDN",
                    "In không dây",
                    "500-1000 trang/tháng",
                    paperSupport = "A4, A6, B5",
                    connectivity = "WiFi, Ethernet",
                    speed = "18 trang/phút",
                    location = "Tòa: D5, phòng: 305",
                    status = false,
                    false,
                    isChecked = false
                ),
                PrinterData(
                    id = 3,
                    "11:30",
                    productName = "Máy in Laser Đa chức năng Epson M3170",
                    "In, scan, copy",
                    "300-600 trang/tháng",
                    paperSupport = "A4, A3, Letter",
                    connectivity = "WiFi Direct, USB 3.0",
                    speed = "16 trang/phút",
                    location = "Tòa: A2, phòng: 404",
                    status = false,
                    false,
                    isChecked = false
                )
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
    fun updateItemStatus(index: Int, newStatus: Boolean) {
        sampleData = sampleData.toMutableList().apply {
            this[index] = this[index].copy(isChecked = newStatus)
        }
    }
    NavigationDrawer{
        Column(
            Modifier.fillMaxSize().padding(top = 110.dp)
        ) {
            SearchBar_PRINTER() {  }
            //SearchBar()
            Spacer(modifier = Modifier.height(10.dp))
            Box(){
                GridList(items = sampleData, onStatusChange = { index, newStatus ->
                    updateItemStatus(index, newStatus)
                })

                Example (
                    onClick = {
                        isEditSubjectDialogOpen = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp)
                )

            }

        }

    }


}
@Composable
fun SearchBar_PRINTER(
    onTextChange : (String) -> Unit
) {
    // Biến lưu trữ giá trị của ô nhập
    var searchText by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
            },
            placeholder = { Text("Nhập địa chỉ máy in...") },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .weight(5f),
            shape = RoundedCornerShape(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                onTextChange(searchText)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1689DC),
                contentColor = Color.White
            ),
            modifier = Modifier.height(50.dp)
        ) {
            Text(text = "Tìm")
        }
    }
}
@Composable
fun GridList(
    items: List<PrinterData>,
    onStatusChange: (Int, Boolean) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            GridItemX(item = items[index], onStatusChange = { newStatus ->
                onStatusChange(index, newStatus)
            })
        }
        item {
            Spacer(modifier = Modifier.height(70.dp))
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
    onCheckedChange: (Boolean) -> Unit,
) {
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,         // Màu của dấu tick (vòng tròn)
            checkedTrackColor = Color(0xFF6200EE),        // Màu nền khi switch bật
            uncheckedThumbColor = Color.White,       // Màu vòng tròn khi không bật
            uncheckedTrackColor = Color.Gray         // Màu nền khi switch tắt
        )
    )
}

@Composable
fun ExampleButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF6200EE),
    contentColor: Color = Color.White
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = modifier
            .width(100.dp)
            .height(40.dp)
            .padding(4.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
    }
}

@Composable
fun GridItemX(item: PrinterData, onStatusChange: (Boolean) -> Unit) {
    val context = LocalContext.current
    var showDialog by rememberSaveable { mutableStateOf(false) }
    if (showDialog) {
        PrinterDetailsDialog(
            printer = item,
            onDismissRequest = { showDialog = false }
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // Hiển thị Toast khi nhấn vào GridItemX
                showDialog = true
            },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)) // Màu xanh nền
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hình ảnh máy in
            Image(
                painter = painterResource(id = R.drawable.printer),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )

            // Cột hiển thị thông tin
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tên máy: ")
                        }
                        append(item.productName) // Phần text thông thường
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
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Khổ giấy: ")
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
            }


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End)
        ) {
            // Nút Xóa
            ExampleButton(
                text = "Xóa",
                onClick = {
                    // Hiển thị Toast khi nhấn nút Xóa
                    Toast.makeText(context, "Đã xóa máy in: ${item.productName}", Toast.LENGTH_SHORT).show()
                },
                backgroundColor = Color.Red
            )


            // Nút Bật/Tắt
            CustomSwitch(
                isChecked = item.isChecked,
                onCheckedChange = { newStatus ->
                    onStatusChange(newStatus)
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

        }
    }
}

@Composable
fun PrinterDetailsDialog(
    printer: PrinterData,
    onDismissRequest: () -> Unit
) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = {
            ExampleButton(
                text = "Đóng",
                onClick = { onDismissRequest() }
            )
        },
        title = {
            Text(text = "Thông tin máy in")
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Tên máy: ${printer.productName}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Địa điểm: ${printer.location}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Khổ giấy: ${printer.paperSupport}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Kết nối: ${printer.connectivity}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Tốc độ in: ${printer.speed}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Tính năng: ${printer.features}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Khuyến nghị: ${printer.recommended}")
            }
        }
    )
}



