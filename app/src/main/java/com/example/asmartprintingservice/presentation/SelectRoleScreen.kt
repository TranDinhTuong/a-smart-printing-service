package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow

@Composable
@Preview(showBackground = true)
fun SelectRoleScreen(modifier: Modifier = Modifier) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(color = Blue),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Column(
            modifier = Modifier
                .size(313.dp, 413.dp)
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(31.dp))
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Login as:",
                color = Blue,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
            )

            Divider(
                color = Blue,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .width(100.dp)
                    .height(1.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Student Button
            Text(
                text = "Student",
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { /* Handle Student Login */ },
                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .size(222.dp, 77.dp)
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_school_24),// Placeholder icon, replace with actual icon for Student
                    contentDescription = "Student Icon",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Admin",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Handle Student Login */ },
                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .size(222.dp, 77.dp)
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_admin_24), // Placeholder icon, replace with actual icon for Student
                    contentDescription = "Student Icon",
                    tint = Color.Blue,
                    modifier = Modifier.size(24.dp)
                )
            }

        }
    }
}