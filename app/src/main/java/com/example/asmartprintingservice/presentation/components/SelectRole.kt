package com.example.asmartprintingservice.presentation.components

import android.widget.ImageButton
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.R

@Preview(showBackground = true)
@Composable
fun SelectRole(
    onClickLogin : () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.logobk),
            contentDescription = null,
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.CenterHorizontally)
        )

        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {

            Box(modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .height(3.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = stringResource(id = R.string.selectRole_dang_nhap),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.scrim,
                modifier = Modifier.padding(5.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    onClickLogin()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onError),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceTint,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(
                    text = "Cán bộ / Sinh viên trường ĐH Bách Khoa Tp.HCM",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    onClickLogin()
                },
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.onError),
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.surfaceTint,
                        shape = MaterialTheme.shapes.medium
                    )
            ) {
                Text(
                    text = "Nhân viên SPSO",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.outlineVariant,
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .padding(horizontal = 5.dp)
                .fillMaxWidth()
                .height(3.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Vietnamese (vi)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                IconButton(
                    onClick = { /*TODO*/ },
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "null"
                    )
                }
                Box(modifier = Modifier
                    .height(22.dp)
                    .width(3.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                )
                
                FilledTonalButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondary),
                    border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "Cookies notice",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.outlineVariant,
                    )
                }
            }

        }

    }
}