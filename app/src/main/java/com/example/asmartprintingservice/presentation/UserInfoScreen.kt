package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.asmartprintingservice.domain.model.UserProfile
import com.example.asmartprintingservice.presentation.auth.AuthEvent
import com.example.asmartprintingservice.presentation.auth.AuthViewModel
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.user.UserEvent
import com.example.asmartprintingservice.presentation.user.UserViewModel
import com.example.asmartprintingservice.util.SnackbarEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


@Composable
fun UserInfoScreen(innerPadding: PaddingValues, userId: String) {

    val userViewModel = hiltViewModel<UserViewModel>()
    val userState by userViewModel.userState.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }


    LaunchedEffect(key1 = true) {
        userViewModel.snackbarEventFlow.collectLatest { event ->
            when (event) {
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
                SnackbarEvent.NavigateUp -> {

                }
            }
        }
    }

    var isEdit by rememberSaveable { mutableStateOf(false) }
    var phoneNumberError by rememberSaveable { mutableStateOf<String?>(null) }
    var fullNameError by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) {it
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Nội dung hiển thị thông tin người dùng
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Thông tin người dùng",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(14.dp))

                UserInfoItem(
                    label = "Email",
                    value = userState.user?.email ?: "test email",
                    isUpdate = false
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = userState.user?.phone_number ?: "test phone",
                    onValueChange = { input ->
                        if (input.all { it.isDigit() } && input.length <= 10) {
                            userViewModel.onEvent(UserEvent.onChangePhoneNumber(input))
                            phoneNumberError = if (input.isEmpty()) {
                                "Số điện thoại không được để trống."
                            } else if (input.length != 10) {
                                "Số điện thoại phải đủ 10 chữ số."
                            } else {
                                null
                            }
                        }
                    },
                    label = {
                        Text(
                            text = "Số điện thoại",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    },
                    enabled = isEdit,
                    isError = phoneNumberError != null,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier.fillMaxWidth(),
                )

                if (phoneNumberError != null) {
                    Text(
                        text = phoneNumberError!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = userState.user?.full_name ?: "test name",
                    onValueChange = { input ->
                        userViewModel.onEvent(UserEvent.onChangeFullName(input))
                        fullNameError = if (input.isEmpty()) {
                            "Tên người dùng không được để trống."
                        } else {
                            null
                        }
                    },
                    label = {
                        Text(
                            text = "Tên người dùng",
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Gray
                        )
                    },
                    enabled = isEdit,
                    isError = fullNameError != null,
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
                    modifier = Modifier.fillMaxWidth(),
                )
                if (fullNameError != null) {
                    Text(
                        text = fullNameError!!,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                UserInfoItem(
                    label = "Số lượng giấy",
                    value = userState.user?.paper.toString() ?: "test paper",
                    isUpdate = false
                )
            }

            // Nút góc trên bên phải
            IconButton(
                onClick = {
                    if(isEdit) {
                        userViewModel.onEvent(UserEvent.updateFullNameAndPhoneNumber)
                    }
                    isEdit = !isEdit
                },
                enabled = phoneNumberError == null && fullNameError == null,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                if (!isEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.Black
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Edit",
                        tint = Color.Black
                    )
                }
            }
        }
    }


}




@Composable
fun PhoneNumberInput(
    label: String = "Số điện thoại",
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    isUpdate: Boolean = true
) {





}

@Composable
fun UserInfoItem(
    label: String,
    value: String,
    isUpdate: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {  },
            label = {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray
                )
            },
            enabled = isUpdate,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
