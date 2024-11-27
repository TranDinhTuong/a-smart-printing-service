package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.domain.model.HistoryData
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.presentation.components.PrintingDatePicker
import com.example.asmartprintingservice.presentation.historyData.HistoryDataEvent
import com.example.asmartprintingservice.presentation.historyData.HistoryDataState
import com.example.asmartprintingservice.presentation.historyData.HistoryDataViewModel
import com.example.asmartprintingservice.util.changeMillisToDateString
import com.example.asmartprintingservice.util.convertDateString
import java.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintingScreen(
    innerPadding: PaddingValues,
    fileId : Int,
    onClickBuyPaper : () -> Unit
) {
    val historyDataViewModel = hiltViewModel<HistoryDataViewModel>()
    val historyDataState = historyDataViewModel.historyDataState.collectAsStateWithLifecycle().value

    val listItem = listOf(
        "A3", "A4", "A5"
    )

    LaunchedEffect(key1 = Unit) {
        historyDataViewModel.onEvent(HistoryDataEvent.countHistoryDataByPrinter)
    }

    var isDatePickerDialogOpen by rememberSaveable { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Instant.now().toEpochMilli()
    ) // lay ra thoi gian hien tai)

    PrintingDatePicker(
        state = datePickerState,
        isOpen = isDatePickerDialogOpen,
        onDismissRequest = { isDatePickerDialogOpen = false },
        onConfirmButtonClicked = {
            historyDataViewModel.onEvent(HistoryDataEvent.onChangeReceiptDate(datePickerState.selectedDateMillis.changeMillisToDateString()))
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
                text = "Tình Trạng" + historyDataState.printerCount,
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
            ){
                Box(
                    modifier = Modifier.size(75.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = 1f,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    CircularProgressIndicator(
                        modifier = Modifier.fillMaxSize(),
                        progress = 0.5f,
                        strokeWidth = 4.dp,
                        strokeCap = StrokeCap.Round,
                        color = Color(0xFF1689DC)
                    )
                    Text(text = "27/30")
                }

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
            ){
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
                        checked = historyDataState.isColor , onCheckedChange = {
                            historyDataViewModel.onEvent(HistoryDataEvent.onChangeColor(it))
                        },
                        colors = CheckboxDefaults.colors(checkedColor = Color.Gray)
                    )
                    Text(
                        text = "In Màu" ,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3A72B4)
                    )
                }

            }
        }
        item {
            DropMenu(
                listItem = listItem,
                content = "a4"
            ){

            }
            Spacer(modifier = Modifier.width(20.dp))
            DropMenu(
                listItem = listItem,
                title = "Cỡ giấy",
                content = historyDataState.paperType
            ){
                historyDataViewModel.onEvent(HistoryDataEvent.onChangePaperType(it))
            }

            Spacer(modifier = Modifier.width(20.dp))

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                value = datePickerState.selectedDateMillis.changeMillisToDateString(),
                onValueChange = {
                    //historyDataViewModel.onEvent(HistoryDataEvent.onChangeReceiptDate(it))
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

            Spacer(modifier = Modifier.width(20.dp))

            CheckBoxItem(title = "In 1 Mặt", title2 = "In 2 Mặt"){
                historyDataViewModel.onEvent(HistoryDataEvent.onChangeSingleSided(it))
            }

            if(historyDataState.isSingleSided){
                Text(text = "In 1 mat true")
            }
        }


        item{
            Spacer(modifier = Modifier.height(20.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Xem Trước ${historyDataState.message}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3A72B4)
                )

                TextButton(
                    onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(6.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1689DC))
                ) {
                    Text(
                        text = "Xem",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }

        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                TextButton(
                    onClick = {
                        historyDataViewModel.onEvent(HistoryDataEvent.saveHistoryData(fileId))
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
fun DropMenu(
    title : String = "Máy In" ,
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

@Preview(showBackground = true, device = Devices.PIXEL_4)
@Composable
fun PreviewPrintingScreen() {
    // Mock state
    val mockHistoryDataState = HistoryDataState(
        isColor = false,
        isSingleSided = true,
        paperType = "A4",
        receiptDate = "14/11/2024",
        message = "Bản xem trước"
    )

    // Mock event handler
    val mockOnEvent: (HistoryDataEvent) -> Unit = { event ->
        println("Event triggered: $event")
    }

    // Call the actual PrintingScreen with mock data
//    PrintingScreen(
//        fileId = 1,
//        historyDataState = mockHistoryDataState,
//        onEvent = mockOnEvent
//    )
}
