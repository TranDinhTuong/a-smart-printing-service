package com.example.asmartprintingservice.presentation.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrintingDatePicker(
    state: DatePickerState,
    isOpen: Boolean,
    confirmButtonText: String = "OK",
    dismissButtonText: String = "Cancel",
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: () -> Unit,
) {
    if (isOpen) {
        DatePickerDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmButtonClicked) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = dismissButtonText)
                }
            },
            content = {
                DatePicker(
                    state = state,

                    // khong cho nguoi dung chon ngay da qua truoc ngay hien tai
                    //selectDate >= currentDate: So sánh ngày đã chọn (selectDate) với ngày hiện tại (currentDate).
                    // Lambda trả về true nếu ngày đã chọn là hôm nay hoặc ngày trong tương lai, và false nếu không.
//                    dateValidator = { timestamp ->
//                        val selectDate = Instant
//                            .ofEpochMilli(timestamp) //Chuyển đổi timestamp từ mili giây thành một Instant, đại diện cho một điểm trong thời gian.
//                            .atZone(ZoneId.systemDefault())//Chuyển đổi Instant thành ZonedDateTime sử dụng múi giờ mặc định của hệ thống. Điều này cần thiết để chuyển đổi một điểm trong thời gian thành một ngày cụ thể.
//                            .toLocalDate() //Trích xuất phần LocalDate từ ZonedDateTime. Điều này cung cấp cho chúng ta phần ngày mà không có thời gian.
//                        val currentDate = LocalDate.now(ZoneId.systemDefault())
//                        selectDate >= currentDate
//                    }
                )
            }
        )
    }
}