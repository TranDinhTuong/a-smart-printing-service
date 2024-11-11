package com.example.asmartprintingservice.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow

import com.example.asmartprintingservice.util.PaperTypeEnum

@Preview(
    showBackground = true,
    device = Devices.PIXEL_5
)
@Composable
fun BuyingScreen(modifier: Modifier = Modifier) {

    var discount by remember {
        mutableStateOf(0)
    }

    var paperPriceType by remember {
        mutableStateOf(PaperTypeEnum.entries[0].price)
    }

    var count by remember {
        mutableStateOf(0)
    }

    //DerivedStateOf giúp tạo ra một trạng thái phụ thuộc vào các trạng thái khác,
    // và nó chỉ được cập nhật khi có sự thay đổi trong các trạng thái phụ thuộc đó.
    val toal by remember {
        derivedStateOf {
            count * paperPriceType * (100 - discount) / 100
        }
    }

    NavigationDrawer {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
                .background(Color(0xFF1488DB).copy(alpha = 0.2f))
                .padding(horizontal = 10.dp)
                .verticalScroll(rememberScrollState()),

        ) {
            Image(
                painter = painterResource(id = R.drawable.page),
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {

                Text(
                    "[New] Giấy trắng Hải Tiến viết là đẹp",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$paperPriceType đ/trang",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        "Mã Giảm Giá",
                        color = Color(0xFF3A72B4),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Discount(){
                        discount = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        "Loại Giấy",
                        color = Color(0xFF3A72B4),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    PaperType(){
                        paperPriceType = it.price
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Số Lượng: ",
                        color = Color(0xFF3A72B4),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    NumberItem(){
                        count = it
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row {
                    Text(
                        "Tổng Tiền:",
                        color = Color(0xFF3A72B4),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Text(
                        " $toal đ",
                        color = Color.Red,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    TextButton(
                        onClick = { /*TODO*/ },
                        border = BorderStroke(1.dp, Blue),
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue)
                    ) {
                        Text(text = "Mua Giấy" ,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Discount (
    modifier: Modifier = Modifier,
    listDiscount : List<Int> = listOf(10,20,30),
    DiscountRequest : (Int) -> Unit = {}
) {

    var selectItem by remember {
        mutableStateOf(-1)
    }

    Row() {
        listDiscount.forEachIndexed { index, it ->
            Box(
                modifier = Modifier
                    .padding(8.dp)
                    .border(
                        BorderStroke(
                            1.dp,
                            if (selectItem == index) Color(0xFFB91C1C) else Color.Transparent
                        ),
                        shape = RoundedCornerShape(3.dp)
                    )
                    .background(Color(0xFFFDEDED))
                    .clickable {
                        selectItem = index
                        DiscountRequest(it)
                    }
            ) {
                Text(
                    text = "$it% GIẢM",
                    color = Color(0xFFB91C1C),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 16.dp)
                )

                Canvas(modifier = Modifier.matchParentSize()) {
                    val size = size
                    val cornerSize = (size.height / 3) * 2 / 3

                    // vẽ bên trái
                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 270f, // Bắt đầu từ 270 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            0f - cornerSize / 2,
                            cornerSize / 2
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )

                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 270f, // Bắt đầu từ 270 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            0f - cornerSize / 2,
                            cornerSize / 2 + size.height / 3
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )

                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 270f, // Bắt đầu từ 270 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            0f - cornerSize / 2,
                            cornerSize / 2 + (size.height / 3) * 2
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )
                    // vẽ bên phải

                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 90f, // Bắt đầu từ 90 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            size.width - cornerSize / 2,
                            cornerSize / 2
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )

                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 90f, // Bắt đầu từ 270 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            size.width - cornerSize / 2,
                            cornerSize / 2 + size.height / 3
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )

                    drawArc(
                        color = Color(0xFF1488DB).copy(alpha = 0.2f), // Màu của vùng cắt
                        startAngle = 90f, // Bắt đầu từ 270 độ để vẽ nửa hình tròn nằm ngang
                        sweepAngle = 180f, // Quét 180 độ để tạo thành nửa hình tròn
                        useCenter = true, // Vẽ từ tâm
                        topLeft = Offset(
                            size.width - cornerSize / 2,
                            cornerSize / 2 + (size.height / 3) * 2
                        ), // Đặt ở giữa cạnh phải
                        size = Size(cornerSize, cornerSize) // Kích thước của nửa hình tròn
                    )
                }
            }
        }
    }

}

@Composable
fun NumberItem(
    modifier: Modifier = Modifier,
    countRequest : (Int) -> Unit
) {
    var count by remember {
        mutableStateOf(0)
    }
    Row (
        modifier = modifier.padding(10.dp)
    ){
        TextButton(
            onClick = {
                if(count > 0){
                    count--
                }
                countRequest(count)
            },
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow,
                contentColor = Blue
            )
        ) {
            Text(text = "-")
        }
        Box(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .size(40.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color(0xFF7749F8))),
            contentAlignment = Alignment.Center,

        ){
            Text(
                text = "$count",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        TextButton(
            onClick = {
                count++
                countRequest(count)
            },
            modifier = Modifier.size(40.dp),
            shape = RoundedCornerShape(6.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Yellow,
                contentColor = Blue
            )
        ) {
            Text(text = "+")
        }
    }
}

@Composable
fun PaperType(
    modifier: Modifier = Modifier,
    PaperTypeRequest : (PaperTypeEnum) -> Unit = {}
) {

    var selectItem by remember {
        mutableStateOf(0)
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        PaperTypeEnum.entries.forEachIndexed { index, it ->
            Box(
                modifier = Modifier
                    .size(100.dp, 60.dp)
                    .border(
                        1.dp,
                        if (selectItem == index) Color(0xFFB91C1C) else MaterialTheme.colorScheme.outlineVariant,
                        RoundedCornerShape(4.dp)
                    )
                    .background(color = Color(0xFFFDEDED))
                    .clickable {
                        selectItem = index
                        PaperTypeRequest(it)
                    }
            ) {
                // Hiển thị số "37" ở giữa
                Text(
                    text = "${it}",
                    color = if(selectItem == index) Color(0xFFB91C1C) else Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

                // Vẽ góc vuông với dấu checkmark ở góc phải dưới
                Canvas(modifier = Modifier.matchParentSize()) {
                    val size = size
                    val cornerSize = size.width / 4

                    // Vẽ tam giác màu đỏ ở góc phải
                    drawPath(
                        path = Path().apply {
                            moveTo(size.width, size.height) // điểm bắt đầu là góc dưới bên phải
                            lineTo(size.width - cornerSize, size.height)
                            lineTo(size.width, size.height - cornerSize)
                            close() //Đóng đường dẫn bằng cách vẽ một đường thẳng từ điểm hiện tại về điểm bắt đầu, tạo thành một hình tam giác.
                        },
                        color = if(selectItem == index) Color(0xFFB91C1C) else Color.Transparent,
                    )

                    // Vẽ dấu checkmark
                    drawPath(
                        path = Path().apply {
                            moveTo(size.width - cornerSize * 0.6f, size.height - cornerSize * 0.3f)
                            lineTo(size.width - cornerSize * 0.4f, size.height - cornerSize * 0.1f)
                            lineTo(size.width - cornerSize * 0.2f, size.height - cornerSize * 0.6f)
                        },
                        color = if(selectItem == index) Color.White else Color.Transparent,
                        style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuyingScreenTopBar(
    onBackButtonClick: () -> Unit
) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackButtonClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate to Back Screen"
                )
            }
        },
        title = {
            Text(text = "Buy Paper ", style = MaterialTheme.typography.headlineSmall)
        }
    )
}