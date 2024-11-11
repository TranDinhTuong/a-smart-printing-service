package com.example.asmartprintingservice.presentation.components

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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun AddPrinterDialog(
    isOpen: Boolean,
    title: String = "Thêm/Cập nhật máy in",
    onDismissRequest: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {


    if (isOpen) {
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
                        value = "",
                        onValueChange = {} ,
                        label = { Text(text = "Tên máy in") },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Ảnh máy in") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Chức năng") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Công suất") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Loại giấy") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Kết nối") },
                        singleLine = true,
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = "",
                        onValueChange = {},
                        label = { Text(text = "Địa điểm") },
                        singleLine = true,
                    )
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