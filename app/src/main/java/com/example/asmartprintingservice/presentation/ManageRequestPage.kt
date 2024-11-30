package com.example.asmartprintingservice.presentation

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.presentation.components.FileTypeScreen
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.InfPrintFile

import com.example.asmartprintingservice.presentation.components.SearchBar
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterEvent


// y chang cái HistoryScreen nhưng có event và nút delete
@Composable
fun ManageRequestPage(
    historyDataState: HistoryDataState,
    innerPadding: PaddingValues,
    onEvent: (HistoryDataEvent) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        Log.d("Launch getAllHistoryData", "come here")
        onEvent(HistoryDataEvent.getAllHistoryData)
    }

    var isInfPrintFileOpen by remember { mutableStateOf(false) }
    // Hộp thoại thông tin
    InfPrintFile(
        isOpen = isInfPrintFileOpen,
        onDismissRequest = {
            isInfPrintFileOpen = false
        }
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(horizontal = 5.dp)
    ) {
        SearchBarRequest(){
            onEvent(HistoryDataEvent.onSerarchHistoryData(it))
        }
        Spacer(modifier = Modifier.height(10.dp))

        if(historyDataState.isLoading){
            IndeterminateCircularIndicator()
        }else{
            if(historyDataState.isSearch){
                RequestList(printData = historyDataState.searchList, onEvent = onEvent, onClickItem = { isInfPrintFileOpen = true })
            }else{
                RequestList(printData = historyDataState.histories, onEvent = onEvent, onClickItem = { isInfPrintFileOpen = true })
            }
        }

    }

}

@Composable
fun RequestList(
    printData: List<HistoryDataDTO>,
    onClickItem: () -> Unit,
    onEvent: (HistoryDataEvent) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        item(){
            Row (modifier =
            Modifier
                .fillMaxWidth()
                .padding(8.dp)
            ){
                Text(text = "Loại tệp", modifier = Modifier.weight(2f), fontWeight = FontWeight.Bold)
                Text(text = "Thời Điểm Yêu Cầu", modifier = Modifier.weight(6f),  fontWeight = FontWeight.Bold)
                Text(text = "Tên tệp in", modifier = Modifier.weight(4f),  fontWeight = FontWeight.Bold)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        items(printData) { data ->
            RequestRow(data, onClickItem = onClickItem, onEvent = onEvent)
        }
    }
}

@Composable
fun RequestRow(
    data: HistoryDataDTO,
    onClickItem : () -> Unit,
    onEvent: (HistoryDataEvent) -> Unit
) {
    var showDeleteDialog by rememberSaveable { mutableStateOf(false) }
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Xác nhận in") },
            text = { Text("In tài liệu ?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onEvent(HistoryDataEvent.deleteHistoryData(data.id))
                        println("In Tài liệu thành công !")
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color.Black, RoundedCornerShape(2.dp))
            .clickable { onClickItem() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = if (data.status) Color.Green else Color.Red

            Canvas(
                modifier = Modifier
                    .size(12.dp)
            ) {
                drawCircle(color = color)
            }
            Image(
                painter = FileTypeScreen(type = data.File?.type ?: ""),
                contentDescription = null,
                modifier = Modifier
                    .weight(2f)
                    .size(50.dp)
            )
            Text(text = data.receiptDate, modifier = Modifier.weight(4f))
            data.File?.let { Text(text = it.name , modifier = Modifier.weight(4f)) }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End)
        ) {
            ExampleButton(
                text = "Xác nhận in",
                onClick = { showDeleteDialog = true },
                backgroundColor = Color.Red
            )
        }
    }
}

@Composable
fun SearchBarRequest(
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
