package com.example.asmartprintingservice.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.data.model.PaperType
import com.example.asmartprintingservice.data.model.PrinterStatus
import com.example.asmartprintingservice.domain.model.Printer
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterEvent
import java.util.UUID

@Composable
fun AddPrinterDialog(
    isOpen: Boolean,
    title: String = "Thêm máy in",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit,
    onEvent: (ManagePrinterEvent) -> Unit
) {


    if (isOpen) {
        val printerName = remember { mutableStateOf("") }
        val printerType = remember { mutableStateOf("") }
        val printerLocation = remember { mutableStateOf("") }
        val trayCapacity = remember { mutableStateOf("") }
        val outputTrayCapacity = remember { mutableStateOf("") }
        val paperSizeLabels = listOf("A0", "A1", "A2", "A3", "A4", "A5")
        val paperTypes = remember { mutableStateOf(List(paperSizeLabels.size) { false }) }
        val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

        // Launcher để chọn ảnh từ máy
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            selectedImageUri.value = uri
        }
        val listPaperType = mutableListOf<PaperType>()
        AlertDialog(
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = title)
            },
            // chua noi dung cua dialog
            text = {
                Column (
                    Modifier.verticalScroll(rememberScrollState())
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                    }
                    OutlinedTextField(
                        value = printerName.value,
                        onValueChange = {printerName.value = it} ,
                        label = { Text(text = "Tên máy in") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = printerType.value,
                        onValueChange = {printerType.value = it},
                        label = { Text(text = "Loại máy") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = printerLocation.value,
                        onValueChange = {printerLocation.value = it},
                        label = { Text(text = "Địa điểm") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = trayCapacity.value,
                        onValueChange = { trayCapacity.value = it},
                        label = { Text(text = "Dung tích khay nạp") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = outputTrayCapacity.value,
                        onValueChange = {outputTrayCapacity.value = it},
                        label = { Text(text = "Dung tích khay chứa") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "Loại giấy:")
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround // Căn chỉnh đều các phần tử
                    ) {
                        val paperSizeLabels = listOf("A0", "A1", "A2", "A3", "A4", "A5")
                        paperSizeLabels.forEachIndexed { index, label ->
                            Column(
                                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                            ) {
                                Checkbox(
                                    checked = paperTypes.value[index],
                                    onCheckedChange = { isChecked ->
                                        paperTypes.value = paperTypes.value.toMutableList().apply {
                                            this[index] = isChecked
                                        }
                                        // Thêm hoặc xóa loại giấy trong danh sách
                                        val paperType = PaperType.valueOf(label) // Giả sử PaperType là enum hoặc có giá trị tương ứng
                                        if (isChecked) {
                                            listPaperType.add(paperType) // Thêm vào danh sách nếu được tick
                                        } else {
                                            listPaperType.remove(paperType) // Xóa khỏi danh sách nếu bị bỏ tick
                                        }
                                    }
                                )
                                Text(text = label) // Hiển thị nhãn bên dưới checkbox
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                    TextButton(onClick = { imagePickerLauncher.launch("image/*") }) {
                        Text(text = selectedImageUri.value?.lastPathSegment ?: "Chọn ảnh máy in")
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // tạo printer ms và edit


                        val printerData = Printer(
                            id = UUID.randomUUID().toString().take(8),
                            name = printerName.value,
                            address = printerLocation.value,
                            machineType =  printerType.value,
                            dungTichKhayNap = trayCapacity.value.toInt(),
                            dungTichKhayChua = outputTrayCapacity.value.toInt(),
                            paperTypes = listPaperType,
                            state = PrinterStatus.OFF
                        )
                        onEvent(ManagePrinterEvent.InsertPrinter(printerData))
                        onConfirmButtonClick()
                    }
                ) {
                    Text(text = "Save")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancel")
                }
            }
        )
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewAddPrinterDialog() {
    val isOpen = remember { mutableStateOf(true) }

    AddPrinterDialog(
        isOpen = isOpen.value,
        onDismissRequest = { isOpen.value = false },
        onConfirmButtonClick = {
            println("Confirm button clicked")
            isOpen.value = false
        },
        onEvent = {

        }
    )
}