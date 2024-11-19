package com.example.asmartprintingservice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow
import com.example.asmartprintingservice.NavigationDrawer

@Composable
fun Home_page_of_admin(modifier: Modifier = Modifier, onClickMenu: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .background(color = Blue)
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    IconButton(
                        onClick = onClickMenu,
                        modifier = Modifier.size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.sort_24px),
                            contentDescription = "Sort Icon",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }

                    Text(
                        text = "Hello, Uy",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "These are our services",
                        color = Color.White,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.width(150.dp))

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.White, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logobk),
                        contentDescription = "Student Icon",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(60.dp))

        Row(
            modifier = modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.width(50.dp))

            Column {
                Text(
                    text = "Manage Printers",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelLarge
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.print_connect_24px),
                        contentDescription = "Manage Printers Icon",
                        tint = Blue,
                        modifier = Modifier.size(57.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(50.dp))

            Column {
                Text(
                    text = "Manage User",
                    color = Color.Black,
                    style = MaterialTheme.typography.labelLarge
                )
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.person_24px),
                        contentDescription = "Manage User Icon",
                        tint = Blue,
                        modifier = Modifier.size(57.dp)
                    )
                }
            }
        }
    }
}
class PaddingValuesProvider : PreviewParameterProvider<PaddingValues> {
    override val values = sequenceOf(PaddingValues())
}
@Preview(showBackground = true)
@Composable
fun Home_page_of_adminPreview(
    @PreviewParameter(PaddingValuesProvider::class) paddingValues: PaddingValues
) {
    MaterialTheme {
        NavigationDrawer()
    }
}
