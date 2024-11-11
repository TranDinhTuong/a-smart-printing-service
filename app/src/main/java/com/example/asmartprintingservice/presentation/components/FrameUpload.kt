package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow

@Composable
@Preview(showBackground = true)
fun FrameUpload(
    modifier: Modifier = Modifier,
    fileName : String = "Lab_6_Wireshark_Ethernet_ARP_v8.0" ,
    fileType : String = "PDF",
    progress : Float = 0.6f,
    color : Color = Color(0xFF28A745),
    onError : Boolean = true,
    onButtonClick : () -> Unit = {},
    onClickItem : () -> Unit = {},
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onClickItem()
            },
        border = BorderStroke(1.dp, color = Color.Black),
        shape = RoundedCornerShape(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(5.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = fileName,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color(0xFF555555),
                    modifier = Modifier.weight(3f)
                )


                Text(
                    text = fileType,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF555555),
                    modifier = Modifier.weight(1f)
                )


                if(onError){
                    Button(
                        onClick =  onButtonClick,
                        modifier = Modifier.height(30.dp),
                        border = BorderStroke(1.dp, color = color),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow, contentColor = Blue)
                    ) {
                        Text(text = "In", style = MaterialTheme.typography.bodyMedium)
                    }
                } else{
                    IconButton(
                        onClick = onButtonClick,
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = "delete",
                            tint = Color.Red
                        )
                    }
                }
            }

            Progress(color = color, progress = progress)

        }
    }

}

@Composable
fun Progress(
    modifier: Modifier = Modifier,
    color: Color,
    progress: Float,
) {
    Box(
        modifier = modifier
            .width(500.dp)
            .padding(3.dp),
    ) {
        LinearProgressIndicator(
            progress = 1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            strokeCap = StrokeCap.Round,
            color = Color.White
        )

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            strokeCap = StrokeCap.Round,
            color = color
        )
    }
}

