package com.example.asmartprintingservice.presentation

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.presentation.settings.SettingsEvent
import com.example.asmartprintingservice.presentation.settings.SettingsViewModel
import com.example.asmartprintingservice.presentation.settings.SettingsState
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.asmartprintingservice.domain.model.AcceptedFileType
import com.example.asmartprintingservice.domain.model.Settings
import com.example.asmartprintingservice.presentation.managePrinter.ManagePrinterEvent
import java.util.Calendar

@Composable
fun Settings(
    viewModel: SettingsViewModel,
    onEvent: (SettingsEvent) -> Unit,
    state: SettingsState
) {

    LaunchedEffect(Unit) {
        onEvent(SettingsEvent.LoadSettings)
    }

    val paperToAdd = remember(state.settings?.paper_to_add) {
        mutableStateOf(state.settings?.paper_to_add?.toString() ?: "")
    }
    val nextDateAddPaper = remember(state.settings?.next_date_add_paper) {
        mutableStateOf(state.settings?.next_date_add_paper?.toString() ?: "")
    }

    val fileTypes = listOf("doc", "docx", "pdf", "xls", "xlsx")

    // Track checked states of each file type
    val checkedStates = remember(state.acceptedFileTypes) {
        mutableStateOf(
            fileTypes.associateWith { fileType ->
                state.acceptedFileTypes.any { it.file_type == fileType && it.settings_id == 1 }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = paperToAdd.value,
            onValueChange = { paperToAdd.value = it },
            label = { Text(text = "Số giấy thêm") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = nextDateAddPaper.value,
            onValueChange = { nextDateAddPaper.value = it },
            label = { Text(text = "Ngày thêm giấy tiếp theo") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(50.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            fileTypes.forEach { fileType ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(horizontal = 8.dp)
                ) {
                    Text(text = fileType, modifier = Modifier.padding(bottom = 4.dp))

                    Checkbox(
                        checked = checkedStates.value[fileType] ?: false,
                        onCheckedChange = { isChecked ->
                            checkedStates.value = checkedStates.value.toMutableMap().apply {
                                this[fileType] = isChecked
                            }
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val paperToAddValue = paperToAdd.value.toIntOrNull() ?: 0
                val nextDateAddPaperValue = nextDateAddPaper.value

                // Tạo danh sách các file type chưa được chọn
                val uncheckedFileTypes = fileTypes.filter {
                    checkedStates.value[it] == false
                }
                val checkedFileTypes = fileTypes.filter {
                    checkedStates.value[it] == true
                }
                // Log hoặc xử lý các file type không được chọn ở đây
                Log.d("UncheckedFileTypes", uncheckedFileTypes.toString())

                // Cập nhật cấu hình với các giá trị từ UI
                val updatedSettings = Settings(
                    id = 1,
                    paper_to_add = paperToAddValue,
                    next_date_add_paper = nextDateAddPaperValue
                )

                // Truyền các giá trị vào ViewModel
                uncheckedFileTypes.forEach { fileType ->
                    // Truyền tên file vào sự kiện
                    viewModel.onEvent(SettingsEvent.RemoveFileType(fileType))
                    Log.d("Filetype_delete", fileType)
                }
                checkedFileTypes.forEach{ fileType ->
                    val file = AcceptedFileType(
                        settings_id = 1,
                        file_type = fileType
                    )
                    viewModel.onEvent(SettingsEvent.AddFileType(file))
                }
                viewModel.onEvent(SettingsEvent.UpdateSettings(updatedSettings))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Lưu cấu hình hệ thống mới")
        }
    }
}

