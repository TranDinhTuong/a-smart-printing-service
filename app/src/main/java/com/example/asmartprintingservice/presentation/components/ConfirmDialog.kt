package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.BorderStroke
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
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Gray
import com.example.asmartprintingservice.ui.theme.Yellow

@Composable
fun ConfirmDialog(
    modifier: Modifier = Modifier,
    isOpen : Boolean,
    bodyText : String,
    title : String,
    onDismissRequest : () -> Unit,
    onConfirmButtonClick : () -> Unit,
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
                        text = "Xác Nhận",
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
                        text = title,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
            },
            text = {
                Text(text = bodyText)
            },
        )
    }
}