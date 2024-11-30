package com.example.asmartprintingservice.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.data.model.PrinterDTO
import com.example.asmartprintingservice.presentation.auth.AuthViewModel
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.presentation.components.PrintingDatePicker

import com.example.asmartprintingservice.presentation.printing.PrintingEvent
import com.example.asmartprintingservice.presentation.printing.PrintingState
import com.example.asmartprintingservice.presentation.printing.PrintingViewModel
import com.example.asmartprintingservice.util.SnackbarEvent
import com.example.asmartprintingservice.util.changeMillisToDateString
import com.example.asmartprintingservice.util.convertDateString
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintingScreen(
    innerPadding: PaddingValues,
    fileId : Int,
    onClickBuyPaper : () -> Unit
) {
    val printingViewModel = hiltViewModel<PrintingViewModel>()
    val printingState = printingViewModel.printingState.collectAsStateWithLifecycle().value

    var localQuantity by remember { mutableStateOf(printingState.printQuantity.toString()) }

    val listItem = listOf(
        "A3", "A4"
    )

    val authViewModel = hiltViewModel<AuthViewModel>()
    val authState = authViewModel.authState.collectAsState().value

    val context = LocalContext.current

    LaunchedEffect(fileId) {
        printingViewModel.getNumPages(fileId)
        printingViewModel.onEvent(PrintingEvent.getPrinter)
        println(authState.user?.id)
    }

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        printingViewModel.snackbarEventFlow.collectLatest {event ->
            when(event){
                SnackbarEvent.NavigateUp -> TODO()
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }

    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    ) // lay ra thoi gian hien tai)
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { it ->
        println(it)
        PrintingDatePicker(
            state = datePickerState,
            isOpen = isDatePickerDialogOpen,
            onDismissRequest = { isDatePickerDialogOpen = false },
            onConfirmButtonClicked = {
                printingViewModel.onEvent(PrintingEvent.onChangeReceiptDate(datePickerState.selectedDateMillis.changeMillisToDateString()))
                isDatePickerDialogOpen = false
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {

            item {
                Text(
                    modifier = Modifier.padding(top = 10.dp),
                    text = "Tình Trạng Giấy",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3A72B4)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PaperStatusBar(
                        100,  // Số giấy đang sở hữu
                        printingState.paperNeeded // Số giấy cần in
                    )
                    TextButton(
                        onClick = { onClickBuyPaper() },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1689DC))
                    ) {
                        Text(
                            text = "Mua Giấy",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            item {

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tùy Chỉnh",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3A72B4)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = printingState.isColored, onCheckedChange = {
                                printingViewModel.onEvent(PrintingEvent.onChangeColor(it))
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Color.Gray)
                        )
                        Text(
                            text = "In Màu",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A72B4)
                        )
                    }
                }
            }
            item {
                // Hiển thị DropMenu nếu có danh sách máy in
                if (printingState.printers.isNotEmpty()) {
                    PrinterDropMenu(
                        title = "Máy In",
                        listItem = printingState.printers,
                        content = printingState.selectedPrinter,
                        onItemSelected = { selectedPrinter ->
                            printingViewModel.onEvent(PrintingEvent.onChangePrinter(selectedPrinter))
                        }
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                DropMenu(
                    listItem = listItem,
                    title = "Cỡ giấy",
                    content = printingState.paperType
                ) {
                    printingViewModel.onEvent(PrintingEvent.onChangePaperType(it))
                }

                Spacer(modifier = Modifier.width(20.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    value = datePickerState.selectedDateMillis.changeMillisToDateString(),
                    onValueChange = {
                        //PrintingViewModel.onEvent(PrintingEvent.onChangeReceiptDate(it))
                    },
                    label = { Text(text = "Chọn ngày nhận ", color = Color.Black) },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    isDatePickerDialogOpen = true
                                }
                                .padding(8.dp),
                            tint = Color.Black
                        )
                    },
                    enabled = false,
                    colors = TextFieldDefaults.colors(Color.Black)
                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    value = localQuantity, // Giá trị hiện tại
                    onValueChange = { newValue ->
                        if (newValue.all { it.isDigit() }) {
                            localQuantity = newValue
                            // Chỉ nhận các ký tự là số
                            val newQuantity = newValue.toIntOrNull() ?: 0
                            printingViewModel.onEvent(
                                PrintingEvent.onChangePrintQuantity(
                                    newQuantity
                                )
                            )
                        }
                    },
                    label = { Text(text = "Nhập số lượng bản in") }, // Tiêu đề phía trên
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // Bàn phím số
                    ),
                    colors = TextFieldDefaults.colors(Color.Black)
                )

                Spacer(modifier = Modifier.width(20.dp))

                CheckBoxItem(title = "In 1 Mặt", title2 = "In 2 Mặt") {
                    printingViewModel.onEvent(PrintingEvent.onChangeSingleSided(it))
                }

            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(
                        onClick = {
                            printingViewModel.saveHistoryData(fileId)
                            println(printingState.selectedPrinter)
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1689DC))
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            text = "IN NGAY",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun CheckBoxItem(
    title: String,
    title2: String,
    isCheck : (Boolean) -> Unit,
) {

    var isChecked by remember {
        mutableStateOf(true)
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    isCheck(it)
                },
                colors = CheckboxDefaults.colors(checkedColor = Color.Gray)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Row (verticalAlignment = Alignment.CenterVertically){
            Checkbox(
                checked = isChecked.not(),
                onCheckedChange = {
                    isChecked = it.not()
                    isCheck(it.not())
                },
                colors = CheckboxDefaults.colors(checkedColor = Color.Gray)
            )
            Text(
                text = title2,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterDropMenu(
    title: String = "Máy In",
    listItem: List<PrinterDTO>,
    content: PrinterDTO?,
    onItemSelected: (PrinterDTO) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .fillMaxWidth(),
            value = content?.name ?: "Chọn máy in",
            onValueChange = {},
            label = { Text(text = title, color = Color.Black) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp),
                    tint = Color.Black
                )
            },
            enabled = false,
            colors = TextFieldDefaults.colors(Color.Black)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            listItem.forEach { printer ->
                DropdownMenuItem(
                    text = { Text(printer.name + " - " + printer.address + " - " + "Số lượng request: " + printer.numberRequest)},
                    onClick = {
                        //println(printer.name)
                        onItemSelected(printer) // Trả về PrinterDTO khi chọn
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun DropMenu(
    title : String = "A3" ,
    listItem : List<String>,
    content : String,
    onItemSelected : (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .clickable { expanded = !expanded }
                .fillMaxWidth(),
            value = content,
            onValueChange = {
                //onItemSelected(it)
            },
            label = { Text(text = title, color = Color.Black) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(8.dp),
                    tint = Color.Black
                )
            },
            enabled = false,
            colors = TextFieldDefaults.colors(Color.Black)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            listItem.forEach {
                DropdownMenuItem(
                    text = { Text(it) },
                    onClick = {
                        onItemSelected(it)
                        expanded = false
                    }
                )
            }
        }
    }
}
@Composable
fun DeterminateProgressBar(
    progress: Float, // Giá trị từ 0f đến 1f
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(15.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun PaperStatusBar(
    paperOwned: Int,
    paperNeeded: Int
) {
    val progress = (paperNeeded.toFloat() /paperOwned.toFloat()).coerceIn(0f, 1f)

    Column() {
        Text(text = "$paperNeeded / $paperOwned")

        DeterminateProgressBar(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth(0.6f) // Chỉ chiếm 60% chiều rộng
                .height(15.dp)
                .clip(RoundedCornerShape(4.dp))
        )
    }
}


@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PreviewPrintingScreen() {
    // Mock state
    val mockPrintingState = PrintingState(
        isColored = false,
        isOneSided = true,
        paperType = "A4",
        receiveDate = "14/11/2024",
    )

    // Mock event handler
    val mockOnEvent: (PrintingEvent) -> Unit = { event ->
        println("Event triggered: $event")
    }

    // Call the actual PrintingScreen with mock data

}
