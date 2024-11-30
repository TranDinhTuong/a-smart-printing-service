package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.presentation.components.FileTypeScreen
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.InfPrintFile
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.presentation.components.SearchBar
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel


@Composable
fun PreviewHistoryScreen(
    innerPadding: PaddingValues,
    userId: String
) {

    val historyDataViewModel = hiltViewModel<HistoryDataViewModel>()
    val historyDataState = historyDataViewModel.historyDataState.collectAsStateWithLifecycle().value

    val sampleState = HistoryDataState(
        isLoading = false,
        isSearch = false,
        histories = listOf(

        )
    )
    HistoryScreen(
        historyDataState = historyDataState,
        innerPadding = innerPadding,
        userId = userId,
        onEvent = historyDataViewModel::onEvent
    )
}



@Composable
fun HistoryScreen(
    historyDataState: HistoryDataState,
    innerPadding: PaddingValues,
    userId : String,
    onEvent: (HistoryDataEvent) -> Unit
) {

    LaunchedEffect(key1 = Unit) {
        onEvent(HistoryDataEvent.getAllHistoryData(userId))
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
        SearchBar(){
            onEvent(HistoryDataEvent.onSerarchHistoryData(it))
        }
        Spacer(modifier = Modifier.height(10.dp))

        if(historyDataState.isLoading){
            IndeterminateCircularIndicator()
        }else{
            if(historyDataState.isSearch){
                PrintList(printData = historyDataState.searchList){
                    isInfPrintFileOpen = true
                }
            }else{
                PrintList(printData = historyDataState.histories){
                    isInfPrintFileOpen = true
                }
            }
        }

    }

}

@Composable
fun PrintList(
    printData: List<HistoryDataDTO>,
    onClickItem: () -> Unit
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
                Text(text = "Thời Điểm In", modifier = Modifier.weight(4f),  fontWeight = FontWeight.Bold)
                Text(text = "Tên tệp in", modifier = Modifier.weight(4f),  fontWeight = FontWeight.Bold)
            }
            Divider(color = Color.Gray, thickness = 1.dp)
        }
        items(printData) { data ->
            PrintRow(data, onClickItem = onClickItem)
        }
    }
}

@Composable
fun PrintRow(
    data: HistoryDataDTO,
    onClickItem : () -> Unit
) {
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
    }
}


@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(1.dp)
            .background(Color.Gray)
    )
}


