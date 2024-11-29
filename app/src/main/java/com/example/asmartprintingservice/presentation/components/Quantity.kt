package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp




@Preview(showBackground = true)
@Composable
fun ProductScreen() {
    var selectedQuantity by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Số lượng sản phẩm: $selectedQuantity", fontSize = 20.sp)

        QuantitySelector(
            initialQuantity = selectedQuantity,
            onQuantityChange = { newQuantity ->
                selectedQuantity = newQuantity
            }
        )
    }
}

@Composable
fun QuantitySelector(
    initialQuantity: Int = 1,
    minQuantity: Int = 1,
    maxQuantity: Int = 100,
    onQuantityChange: (Int) -> Unit
) {
    var quantity by remember { mutableStateOf(initialQuantity) }
    var textFieldValue by remember { mutableStateOf(TextFieldValue(quantity.toString())) }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                if (quantity > minQuantity) {
                    quantity -= 1
                    textFieldValue = TextFieldValue(quantity.toString())
                    onQuantityChange(quantity)
                }
            },
            enabled = quantity > minQuantity
        ) {
            Text(text = "-")
        }

        Spacer(modifier = Modifier.width(8.dp))

        BasicTextField(
            value = textFieldValue,
            onValueChange = {
                val input = it.text.toIntOrNull()
                if (input != null && input in minQuantity..maxQuantity) {
                    quantity = input
                    onQuantityChange(quantity)
                }
                textFieldValue = TextFieldValue(it.text.filter { char -> char.isDigit() })
            },
            modifier = Modifier
                .width(60.dp)
                .height(40.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(
            onClick = {
                if (quantity < maxQuantity) {
                    quantity += 1
                    textFieldValue = TextFieldValue(quantity.toString())
                    onQuantityChange(quantity)
                }
            },
            enabled = quantity < maxQuantity
        ) {
            Text(text = "+")
        }
    }
}
