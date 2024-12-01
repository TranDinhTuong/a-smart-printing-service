package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.asmartprintingservice.data.model.TransactionDTO
import com.example.asmartprintingservice.presentation.transaction.TransactionViewModel
import com.example.asmartprintingservice.util.convertToTimezone


@Composable
fun TransactionScreen(innerPadding : PaddingValues,userId: String) {
    val viewModel = hiltViewModel<TransactionViewModel>()
    val latestTransactions by viewModel.latestTransactions.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchTransactions(userId)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).padding(innerPadding)) {
        if (latestTransactions.isEmpty()) {
            Text(text = "Không có giao dịch nào trong tháng")
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            // Hiển thị danh sách 10 giao dịch gần nhất
            LatestTransactionList(transactions = latestTransactions)
        }
    }
}

@Composable
fun LatestTransactionList(transactions: List<TransactionDTO>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "10 Giao dịch gần nhất",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyColumn {
            items(transactions) { transaction ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = "Mã: ${transaction.transactionCode}")
                        Text(text = "Loại giấy: ${transaction.paperType}")
                        Text(text = "Số lượng: ${transaction.amount}")
                        Text(text = "Tổng tiền: ${transaction.totalAmount}")
                    }
                    Text(text = convertToTimezone(transaction.created_at, "Asia/Ho_Chi_Minh"))
                }

                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}

