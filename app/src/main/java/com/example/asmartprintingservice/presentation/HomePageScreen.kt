package com.example.composetutorial.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.R
import com.example.composetutorial.ui.theme.Blue
import com.example.composetutorial.ui.theme.Yellow
import androidx.lifecycle.viewmodel.compose.viewModel
/*import com.example.composetutorial.viewmodel.HomePageViewModel*/

@Preview(showBackground = true)
@Composable
fun HomePageScreenPreview() {
    HomePageScreen(
        onStudentClick = { /*  */ },
        onAdminClick = { /*  */ }
    )
}

@Composable
fun HomePageScreen(
    onStudentClick: () -> Unit,
    onAdminClick: () -> Unit
) {
    /*val viewModel: HomePageViewModel = viewModel()*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Blue),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(87.dp))
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(249.dp)
        ) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                drawCircle(color = Color.White, radius = size.minDimension / 2)
            }

            Image(
                painter = painterResource(id = R.drawable.logobk),
                contentDescription = null,
                modifier = Modifier.size(249.dp)
            )
        }

        Spacer(modifier = Modifier.size(87.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "WELCOME TO\n SMART PRINTING SERVICE",
                color = Yellow,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineMedium,
            )
        }

        Spacer(modifier = Modifier.size(20.dp))

        // Button Student
        Button(
            onClick = onStudentClick,
            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
            shape = RoundedCornerShape(124.dp),
            modifier = Modifier.size(250.dp, 90.dp)
        ) {
            Text(
                text = "STUDENT",
                color = Color(0xFF030391),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Spacer(modifier = Modifier.size(81.dp))

        // Button Admin
        Button(
            onClick = onAdminClick,
            colors = ButtonDefaults.buttonColors(containerColor = Yellow),
            shape = RoundedCornerShape(124.dp),
            modifier = Modifier.size(250.dp, 90.dp)
        ) {
            Text(
                text = "ADMIN",
                color = Color(0xFF030391),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}