package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Gray

@Preview(showBackground = true)
@Composable
fun ChatScreen() {

    NavigationDrawer {
        Column (
            modifier = Modifier.padding(it)
        ){
            Text(text = "Print Assist")
            LazyColumn (

            ){
                item {
                    MessageSend()
                    MessageReceiver()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageSend() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(26.dp, 26.dp, 0.dp, 26.dp))
            .background(Blue)
            .padding(10.dp)
    ) {
        Text(text = "Hello xin chao moi nguoi", style = MaterialTheme.typography.headlineSmall, color = Color.White)
    }
}

@Preview(showBackground = true)
@Composable
fun MessageReceiver() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(26.dp, 26.dp, 26.dp, 0.dp))
            .background(Gray)
            .padding(10.dp)
    ) {
        Text(text = "Hello xin chao moi nguoi", style = MaterialTheme.typography.headlineSmall, color = Color.Black)
    }
}