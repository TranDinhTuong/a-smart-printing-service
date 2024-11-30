package com.example.asmartprintingservice.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.asmartprintingservice.presentation.manageRequest.ManageRequestEvent
import com.example.asmartprintingservice.presentation.manageRequest.ManageRequestState
import com.example.asmartprintingservice.presentation.manageRequest.ManageRequestViewModel

// y chang cái HistoryScreen nhưng có event và nút delete
@Composable
fun ManageRequestPage(
    manageRequestState: ManageRequestState,
    onEvent: (ManageRequestEvent) -> Unit,
    viewModel: ManageRequestViewModel = hiltViewModel()
)
{

}