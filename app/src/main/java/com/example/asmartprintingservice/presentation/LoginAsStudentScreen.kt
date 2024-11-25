package com.example.composetutorial.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.R
import com.example.composetutorial.ui.theme.Blue
import com.example.composetutorial.ui.theme.Yellow

@Preview(showBackground = true)
@Composable
fun LoginAsStudentScreen(modifier: Modifier = Modifier) {
    var accountName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Blue)
            .clickable { focusManager.clearFocus() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))

            // Icon Admin
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(90.dp)
            ) {
                Canvas(
                    modifier = Modifier.matchParentSize()
                ) {
                    drawCircle(color = Yellow, radius = size.minDimension / 2)
                }

                Icon(
                    painter = painterResource(id = R.drawable.baseline_school_24),
                    contentDescription = "User Icon",
                    tint = Blue,
                    modifier = Modifier.size(57.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Admin Login Text
            Text(
                text = "STUDENT LOGIN",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(80.dp))

            // Account Name Field
            OutlinedTextField(
                value = accountName,
                onValueChange = { accountName = it },
                label = {
                    Text(
                        "Account name",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) accountName = "" // Xoá nội dung khi nhận focus
                    },
                colors = TextFieldDefaults.colors(Color.White)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Password Field
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Text(
                        "Password",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                },
                singleLine = true,
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                textStyle = MaterialTheme.typography.headlineMedium.copy(color = Color.Black),
                trailingIcon = {
                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (isPasswordVisible) R.drawable.visibility_24px else R.drawable.visibility_off_24px
                            ),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
                    .onFocusChanged { focusState ->
                        if (focusState.isFocused) password = "" // Xoá nội dung khi nhận focus
                    },
                colors = TextFieldDefaults.colors(Color.White)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Remember Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Yellow,
                        uncheckedColor = Color.White
                    )
                )

                Text(
                    text = "Remember account",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(70.dp))

            // Login Button
            Button(
                onClick = { /* TODO: Handle login logic */ },
                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(67.dp)
            ) {
                Text(
                    text = "Login",
                    color = Color(0xFF030391),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}
