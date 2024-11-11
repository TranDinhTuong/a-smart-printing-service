package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Gray
import com.example.asmartprintingservice.ui.theme.Yellow

@Composable
fun InfPrintFile(
    isOpen : Boolean,
    onDismissRequest : () -> Unit,
) {
    if(isOpen){
        Dialog(
            onDismissRequest = onDismissRequest
        ){
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "ID : 1",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(text = "Tên tệp : 1", style = MaterialTheme.typography.titleMedium)
                Text(text = "Số trang : 1", style = MaterialTheme.typography.titleMedium)
                Text(text = "Tên máy in: ", style = MaterialTheme.typography.titleMedium)
                Text(text = "Thời điểm in: ", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}