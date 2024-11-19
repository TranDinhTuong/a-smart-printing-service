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
import androidx.compose.material3.AlertDialog
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.data.model.PrinterStatus
import com.example.asmartprintingservice.presentation.components.AddPrinterDialog
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.presentation.components.SearchBar
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterEvent
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterState
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterViewModel



@Composable
fun showToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
@Composable
fun ManagePrinterPage(
    managePrinterState: ManagePrinterState,
    onEvent: (ManagePrinterEvent) -> Unit,
    viewModel: ManagePrinterViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = Unit) {
        onEvent(ManagePrinterEvent.LoadPrinters)
    }

    var isEditSubjectDialogOpen by rememberSaveable { mutableStateOf(false) }

    AddPrinterDialog(
        isOpen = isEditSubjectDialogOpen,
        onDismissRequest = { isEditSubjectDialogOpen = false },
        onConfirmButtonClick = {
            isEditSubjectDialogOpen = false
        }
    )

    NavigationDrawer{
        Column(
            Modifier.fillMaxSize().padding(top = 110.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            SearchBar_PRINTER() {
                onEvent(ManagePrinterEvent.SearchPrinters(it))
            }
            //SearchBar()
            Spacer(modifier = Modifier.height(10.dp))
            Box(){
                showToast("Bắt đầu box")
                if(managePrinterState.isLoading){
                    showToast("Tải dữ liệu")
                    IndeterminateCircularIndicator()
                }else
                {
                    showToast("Tải xong")
                    if(managePrinterState.isSearch)
                    {
                        showToast("Is Search True")
                        GridList(items = managePrinterState.search, onEvent = onEvent, viewModel = viewModel)
                    }
                    else{
                        showToast("Is Search False")
                        showToast(managePrinterState.printers.toString())
                        GridList(items = managePrinterState.printers, onEvent = onEvent, viewModel = viewModel)
                    }

                }

                // Dấu cộng để thêm máy in
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
    items: List<PrinterDTO>,
    onEvent: (ManagePrinterEvent) -> Unit,
    viewModel: ManagePrinterViewModel
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.size) { index ->
            GridItemX(item = items[index], onEvent = onEvent, viewModel = viewModel)
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
    contentColor: Color = Color.White,

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
fun GridItemX(item: PrinterDTO, onEvent: (ManagePrinterEvent) -> Unit, viewModel: ManagePrinterViewModel) {
    var showDialog by rememberSaveable { mutableStateOf(false) }
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }

    val isSwitchChecked = viewModel.getPrinterState(item.id)

    if (showDialog) {
        PrinterDetailsDialog(
            printer = item,
            onDismissRequest = { showDialog = false }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa máy in: ${item.name}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onEvent(ManagePrinterEvent.DeletePrinters(item.id))
                        println("Đã xóa máy in: ${item.name}")
                    }
                ) {
                    Text("Xác nhận")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) {
                    Text("Hủy")
                }
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDialog = true },
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.printer),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .padding(end = 8.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Tên máy: ")
                        }
                        append(item.name)
                    },
                    modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
                )
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append("Địa điểm: ")
                        }
                        append(item.address)
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
            ExampleButton(
                text = "Xóa",
                onClick = { showDeleteDialog = true },
                backgroundColor = Color.Red
            )

            CustomSwitch(
                isChecked = isSwitchChecked,
                onCheckedChange = { newStatus ->
                    // Cập nhật trạng thái trong ViewModel
                    viewModel.updatePrinterState(item.id, newStatus)
                    onEvent(ManagePrinterEvent.UpdatePrinterStatus(item, newStatus))
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}


@Composable
fun PrinterDetailsDialog(
    printer: PrinterDTO,
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
                Text(text = "Tên máy: ${printer.name}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Địa điểm: ${printer.address}")
                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    )
}


