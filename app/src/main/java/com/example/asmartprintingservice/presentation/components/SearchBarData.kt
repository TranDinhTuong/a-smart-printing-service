package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarData(
    title : String,
    onTextChange : (String) -> Unit
) {
    // Biến lưu trữ giá trị của ô nhập
    var searchText by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { newText ->
                 searchText = newText
            },
            placeholder = { Text(title) },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth()
                .weight(5f),
            shape = RoundedCornerShape(16.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(
            onClick = {
                  onTextChange(searchText)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1689DC),
                contentColor = Color.White
            ),
            modifier = Modifier.height(50.dp)
        ) {
            Text(text = "Tìm")
        }
    }
}