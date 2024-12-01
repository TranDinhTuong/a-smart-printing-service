



package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import com.example.asmartprintingservice.data.model.PaperType
import com.example.asmartprintingservice.presentation.components.FileTypeScreen
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.InfPrintFile
import com.example.asmartprintingservice.presentation.components.SearchBar
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.presentation.historyData.StudentHistoryViewModel
import com.example.asmartprintingservice.presentation.printing.PrintingViewModel
import com.example.asmartprintingservice.presentation.studentHistory.HistoryWithStudentName

@Composable
fun HistoryStudentScreen(
    innerPadding: PaddingValues
    //onEvent: (HistoryDataEvent) -> Unit
) {
    val studentHistoryViewModel = hiltViewModel<StudentHistoryViewModel>()
    val studentHistoryState = studentHistoryViewModel.studentHistoryState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        //onEvent(HistoryDataEvent.getAllHistoryData)
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
            //onEvent(HistoryDataEvent.onSerarchHistoryData(it))
        }
        Spacer(modifier = Modifier.height(10.dp))

        if(studentHistoryState.isLoading){
            IndeterminateCircularIndicator()
        }else{
            if(studentHistoryState.isSearch){
                HistoryStudentList(printData = studentHistoryState.searchList){
                    isInfPrintFileOpen = true
                }
            }else{
                HistoryStudentList(printData = studentHistoryState.histories){
                    isInfPrintFileOpen = true
                }
            }
        }

    }

}

@Composable
fun HistoryStudentList(
    printData: List<HistoryWithStudentName>,
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
                Text(text = "Tên sinh viên", modifier = Modifier.weight(8f), fontWeight = FontWeight.Bold)
                Text(text = "Loại tệp", modifier = Modifier.weight(4f), fontWeight = FontWeight.Bold)
                Text(text = "Thời điểm in", modifier = Modifier.weight(4f),  fontWeight = FontWeight.Bold)
                Text(text = "Tên tệp", modifier = Modifier.weight(4f),  fontWeight = FontWeight.Bold)
                Text(text = "Loại giấy", modifier = Modifier.weight(4f), fontWeight = FontWeight.Bold)
                Text(text = "Số lượng giấy", modifier = Modifier.weight(4f), fontWeight = FontWeight.Bold)
            }
            HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        }
        items(printData) { data ->
            PrintHistoryStudentRow(data, onClickItem = onClickItem)
        }
    }
}

@Composable
fun PrintHistoryStudentRow(
    data: HistoryWithStudentName,
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

@Preview
@Composable
fun PreviewStudentHistoryScreen(
) {


    val sampleState = HistoryDataState(
        isLoading = false,
        isSearch = false,
        histories = listOf(
            HistoryDataDTO(1, "a1", true, true, "1/1/2023", true, 11, numberPages = 1, numberPrints = 4),
        )
    )

    HistoryStudentScreen(
        historyDataState = sampleState,
        innerPadding = PaddingValues(16.dp)
    )
}