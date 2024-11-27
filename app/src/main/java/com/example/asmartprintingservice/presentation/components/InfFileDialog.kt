package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Gray
import com.example.asmartprintingservice.ui.theme.Yellow

@Preview(showBackground = true)
@Composable
fun InfFileDialog(
    isOpen : Boolean = true,
    file : FileDTO = FileDTO(1, "x", "x", "x", 0),
    onDismissRequest : () -> Unit = {},
    onConfirmButtonClick : () -> Unit = {},
) {
    if(isOpen){
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(
                    onClick = onConfirmButtonClick,
                    border = BorderStroke(1.dp, color = Yellow),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow)
                ) {
                    Text(
                        text = "Xóa File",
                        fontWeight = FontWeight.Bold,
                        color = Blue
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = onDismissRequest,
                    border = BorderStroke(1.dp, color = Gray),
                            shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gray)
                ) {
                    Text(
                        text = "Hủy Bỏ",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            title = { 
                    Text(
                        text = "Thông Tin File",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
            },
            text = {
                Column {
                    Text(text = "Tên File: ${file.name}")
                    Text(text = "Loại File: ${file.type}")
                    Text(text = "Thời Gian tải: ${file.time}")
                }
            },
        )
    }
}