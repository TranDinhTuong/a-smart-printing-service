package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.presentation.PrinterHistory.PrinterHistoryEvent
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.InfPrintFile
import com.example.asmartprintingservice.presentation.components.PrintingDatePicker
import com.example.asmartprintingservice.presentation.components.SearchBar
import com.example.asmartprintingservice.presentation.historyData.PrinterHistoryViewModel
import com.example.asmartprintingservice.presentation.studentHistory.PrinterHistoryState
import com.example.asmartprintingservice.util.changeMillisToDateString
import com.example.asmartprintingservice.util.convertDateString
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinterHistoryScreen(
    //printerHistoryState: printerHistoryState,
    innerPadding: PaddingValues
    //onEvent: (HistoryDataEvent) -> Unit
) {
    val printerHistoryViewModel = hiltViewModel<PrinterHistoryViewModel>()
    val printerHistoryState = printerHistoryViewModel.printerHistoryState.collectAsStateWithLifecycle().value

    var isStartDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isEndDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }

    val startDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().minus(30, ChronoUnit.DAYS).toEpochMilli()
    )

    val endDatePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    )

    LaunchedEffect(key1 = Unit) {
        printerHistoryViewModel.onEvent(PrinterHistoryEvent.getAllPrinterHistory)
    }

    println("size")
    println(printerHistoryState.histories.size)

    var isInfPrintFileOpen by remember { mutableStateOf(false) }
    // Hộp thoại thông tin
    InfPrintFile(
        isOpen = isInfPrintFileOpen,
        onDismissRequest = {
            isInfPrintFileOpen = false
        }
    )

    PrintingDatePicker(
        state = startDatePickerState,
        isOpen = isStartDatePickerDialogOpen,
        onDismissRequest = { isStartDatePickerDialogOpen = false},
        onConfirmButtonClicked = {
            printerHistoryViewModel.onEvent(PrinterHistoryEvent.onChangeStartDate(startDatePickerState.selectedDateMillis.changeMillisToDateString()))
            isStartDatePickerDialogOpen = false
        }
    )

    PrintingDatePicker(
        state = endDatePickerState,
        isOpen = isEndDatePickerDialogOpen,
        onDismissRequest = { isEndDatePickerDialogOpen = false },
        onConfirmButtonClicked = {
            printerHistoryViewModel.onEvent(PrinterHistoryEvent.onChangeEndDate(endDatePickerState.selectedDateMillis.changeMillisToDateString()))
            isEndDatePickerDialogOpen = false
        }
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 5.dp)
    ) {
        SearchBar("Nhập tên máy in"){
            printerHistoryViewModel.onEvent(PrinterHistoryEvent.onSerarchPrinterHistory(it))
        }
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .weight(1f),
                value = startDatePickerState.selectedDateMillis.changeMillisToDateString(),
                onValueChange = {
                    //PrintingViewModel.onEvent(PrintingEvent.onChangeReceiptDate(it))
                },
                label = { Text(text = "Từ ngày", color = Color.Black) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                isStartDatePickerDialogOpen = true
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
                    .padding(vertical = 10.dp)
                    .weight(1f),
                value = endDatePickerState.selectedDateMillis.changeMillisToDateString(),
                onValueChange = {
                    //PrintingViewModel.onEvent(PrintingEvent.onChangeReceiptDate(it))
                },
                label = { Text(text = "Đến ngày", color = Color.Black) },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        modifier = Modifier
                            .clickable {
                                isEndDatePickerDialogOpen = true
                            }
                            .padding(8.dp),
                        tint = Color.Black
                    )
                },
                enabled = false,
                colors = TextFieldDefaults.colors(Color.Black)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth(), // Chiếm toàn bộ màn hình
            contentAlignment = Alignment.Center // Căn giữa nội dung
        ) {
            Button(
                onClick = {
                    printerHistoryViewModel.onEvent(PrinterHistoryEvent.onFilterDatePrinterHistory(
                        printerHistoryState.startDate, printerHistoryState.endDate)
                    )
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1689DC),
                    contentColor = Color.White
                ),
                modifier = Modifier.height(50.dp)
            ) {
                Text(text = "Lọc theo ngày")
            }
        }

        if(printerHistoryState.isLoading){
            IndeterminateCircularIndicator()
        }else{
            if(printerHistoryState.isSearch){
                HistoryPrinterList(printData = printerHistoryState.searchList){
                    isInfPrintFileOpen = true
                }
            }else{
                HistoryPrinterList(printData = printerHistoryState.filteredDateList){
                    isInfPrintFileOpen = true
                }
            }
        }

    }

}

@Composable
fun HistoryPrinterList(
    printData: List<HistoryDataDTO>,
    onClickItem: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item(){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(
                    text = "Tên máy in",
                    modifier = Modifier.weight(4f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Số tờ",
                    modifier = Modifier.weight(2f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Loại giấy",
                    modifier = Modifier.weight(2f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Thời điểm in",
                    modifier = Modifier.weight(4f),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
        items(printData) { data ->
            PrintHistoryPrinterRow(data, onClickItem = onClickItem)
        }
    }
}

@Composable
fun PrintHistoryPrinterRow(
    data: HistoryDataDTO,
    onClickItem : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .clickable { onClickItem() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            data.Printer?.let { Text(text = it.name, modifier = Modifier.weight(4f)) }
            Text(text = data.numberPages.toString(), modifier = Modifier.weight(2f))
            Text(text = data.paperType, modifier = Modifier.weight(2f))
            Text(text = data.receiptDate, modifier = Modifier.weight(4f))
        }
    }
}

@Preview
@Composable
fun PreviewPrinterHistoryScreen(
) {

    val sampleState = PrinterHistoryState(
        isLoading = false,
        isSearch = false,
        histories = listOf(
            HistoryDataDTO(
                id = 1,
                status = true,
                File = FileDTO(1, "aaa", "pdf", "1/1/2222", 13),
                receiptDate = "1/1/2023",
                paperType = "A4",
                numberPages = 12,
                isColor = TODO(),
                isSingleSided = TODO(),
                file_id = TODO(),
                printer_id = TODO(),
                Printer = TODO(),
                userId = TODO(),
                User = TODO(),
                numberPrints = TODO()
            )
        )
    )

    //HistoryStudentScreen(
    //sampleState,
    //innerPadding = PaddingValues(16.dp)
    //)
}