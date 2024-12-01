package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.domain.model.User
import com.example.asmartprintingservice.domain.model.UserProfile
import com.example.asmartprintingservice.presentation.auth.AuthEvent
import com.example.asmartprintingservice.presentation.auth.AuthState
import com.example.asmartprintingservice.presentation.auth.AuthViewModel
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow
import com.example.asmartprintingservice.util.SnackbarEvent
import kotlinx.coroutines.flow.collectLatest
import android.content.Context

@Composable
fun LoginAsStudentScreen(
    onNavigateToMainScreen: (UserProfile?) -> Unit
) {
    val authViewModel = hiltViewModel<AuthViewModel>()
    val authState by authViewModel.authState.collectAsState()

//    var accountName by remember { mutableStateOf("") }
//    var password by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("login_prefs", Context.MODE_PRIVATE)

    var accountName by remember {
        mutableStateOf(
            sharedPreferences.getString("accountName", "") ?: ""
        )
    }
    var password by remember { mutableStateOf(sharedPreferences.getString("password", "") ?: "") }
    var rememberAccount by remember {
        mutableStateOf(
            sharedPreferences.getBoolean(
                "rememberAccount",
                false
            )
        )
    }


    LaunchedEffect(key1 = true) {
        authViewModel.snackbarEventFlow.collectLatest { event ->
            when (event) {
                SnackbarEvent.NavigateUp -> {
                    onNavigateToMainScreen(authState.user)
                }

                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .background(Blue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(70.dp))
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
                    painter = painterResource(id = R.drawable.baseline_school_24),// Placeholder icon, replace with actual icon for Student
                    contentDescription = "Student Icon",
                    tint = Blue,
                    modifier = Modifier.size(57.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "LOGIN",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(80.dp))

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(67.dp),
                    colors = TextFieldDefaults.colors(Color.Black)
                )

                Spacer(modifier = Modifier.height(20.dp))

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
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(67.dp),
                    colors = TextFieldDefaults.colors(Color.Black),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(id = R.drawable.baseline_visibility_24) else painterResource(
                                    id = R.drawable.baseline_visibility_off_24
                                ),
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        modifier = Modifier.size(64.dp),
                        checked = rememberAccount,
                        onCheckedChange = {
                            rememberAccount = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            checkedColor = Color.Red,
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

                Box(
                    contentAlignment = Alignment.Center,
                ) {
                    if (authState.isLoading) {
                        IndeterminateCircularIndicator()
                    }
                    Button(
                        onClick = {

                            if (rememberAccount) {
                                val editor = sharedPreferences.edit()
                                editor.putString("accountName", accountName)
                                editor.putString("password", password)
                                editor.putBoolean("rememberAccount", rememberAccount)
                                editor.apply()
                            } else {
                                val editor = sharedPreferences.edit()
                                editor.clear()
                                editor.apply()
                            }

                            // Gửi sự kiện đăng nhập
                            try {
                                authViewModel.onEvent(
                                    AuthEvent.SignIn(
                                        email = accountName,
                                        password = password
                                    )
                                )
                            } catch (e: Exception) {
                                println(e.message)
                            }
                        },
                        enabled = if (!authState.isLoading) true else false,
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(67.dp)
                    ) {
                        Text(
                            text = "Đăng nhập",
                            color = Color(0xFF030391),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineSmall
                        )
                    }
                }

            }
        }
    }
}