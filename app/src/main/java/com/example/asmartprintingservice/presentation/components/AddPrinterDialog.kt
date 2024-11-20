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
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterEvent

@Composable
fun AddPrinterDialog(
    isOpen: Boolean,
    onEvent: (ManagePrinterEvent) -> Unit,
    title: String = "Thêm/Cập nhật máy in",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {


    if (isOpen) {
        val printerName = remember { mutableStateOf("") }
        val printerType = remember { mutableStateOf("") }
        val printerLocation = remember { mutableStateOf("") }
        val trayCapacity = remember { mutableStateOf("") }
        val outputTrayCapacity = remember { mutableStateOf("") }
        val paperTypes = remember { mutableStateOf(List(6) { false }) } // A0 -> A5
        val selectedImageUri = remember { mutableStateOf<Uri?>(null) }

        // Launcher để chọn ảnh từ máy
        val imagePickerLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri ->
            selectedImageUri.value = uri
        }
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
                        onValueChange = {} ,
                        label = { Text(text = "Tên máy in") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = printerType.value,
                        onValueChange = {},
                        label = { Text(text = "Loại máy") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = printerLocation.value,
                        onValueChange = {},
                        label = { Text(text = "Địa điểm") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = trayCapacity.value,
                        onValueChange = {},
                        label = { Text(text = "Dung tích khay nạp") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = outputTrayCapacity.value,
                        onValueChange = {},
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
                                    onCheckedChange = {
                                        paperTypes.value = paperTypes.value.toMutableList().apply {
                                            this[index] = it
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
                    onClick = onConfirmButtonClick,
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
        onEvent = { event ->
            // Xử lý sự kiện (có thể thay đổi theo logic của bạn)
            println("Event: $event")
        },
        onConfirmButtonClick = {
            println("Confirm button clicked")
            isOpen.value = false
        }
    )
}