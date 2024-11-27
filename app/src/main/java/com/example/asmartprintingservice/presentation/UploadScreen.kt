package com.example.asmartprintingservice.presentation

import android.content.Context
import android.content.Intent
import android.graphics.pdf.PdfRenderer
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.asmartprintingservice.presentation.components.ConfirmDialog
import com.example.asmartprintingservice.presentation.components.FrameUpload
import com.example.asmartprintingservice.presentation.components.NavigationDrawer
import com.example.asmartprintingservice.ui.theme.Blue
import com.example.asmartprintingservice.ui.theme.Yellow

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.asmartprintingservice.data.model.FileDTO
import com.example.asmartprintingservice.presentation.components.IndeterminateCircularIndicator
import com.example.asmartprintingservice.presentation.components.InfFileDialog
import com.example.asmartprintingservice.presentation.file.FileEvent
import com.example.asmartprintingservice.presentation.file.FileState
import com.example.asmartprintingservice.presentation.file.FileViewModel
import com.example.asmartprintingservice.util.Route
import com.example.asmartprintingservice.util.SnackbarEvent
import com.example.asmartprintingservice.util.getFileName
import com.example.asmartprintingservice.util.uriToByteArray
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xwpf.usermodel.XWPFDocument
import java.time.LocalDate
import java.time.format.DateTimeFormatter



@Composable
fun UploadScreen(
    innerPadding: PaddingValues,
    onItemSelected: (Int?) -> Unit
) {
    val fileViewModel = hiltViewModel<FileViewModel>()
    val fileState = fileViewModel.fileState.collectAsStateWithLifecycle().value

    var isConfirmDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isInfFileDialogOpen by rememberSaveable { mutableStateOf(false) }
    var isConfirmDialogUpload by rememberSaveable { mutableStateOf(false) }

    ConfirmDialog(
        isOpen = isConfirmDialogOpen,
        title = "Xác nhận In",
        bodyText = "Bạn muốn In không?",
        onDismissRequest = { isConfirmDialogOpen = false },
        onConfirmButtonClick = {
            //navController.navigate(Route.Printing.name)
            onItemSelected(fileState.fileCurrentId)
            isConfirmDialogOpen = false
        }
    )


    LaunchedEffect(key1 = fileState.isDelete, key2 = fileState.isUpload) {
        fileViewModel.onEvent(FileEvent.LoadFiles)
    }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val selectedFileUri = remember  { mutableStateOf<Uri?>(null) }
    val fileAsByteArray = remember  { mutableStateOf<ByteArray?>(null) }
    val selectedFileName = remember { mutableStateOf<String?>(null) }
    val numberPagesFile = remember  { mutableStateOf<Int?>(null)}

    fun getPdfPageCount(context: Context, uri: Uri): Int {
        return try {
            context.contentResolver.openFileDescriptor(uri, "r")?.use { fileDescriptor ->
                PdfRenderer(fileDescriptor).use { renderer ->
                    renderer.pageCount // Trả về số trang của tệp PDF
                }
            } ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0 // Trả về 0 nếu có lỗi
        }
    }
    fun getDocxPageCount(context: Context, uri: Uri): Int {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val document = XWPFDocument(inputStream)
                document.properties.extendedProperties.pages // Trả về số trang của tệp DOCX
            } ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0 // Trả về 0 nếu có lỗi
        }
    }

    fun getExcelSheetCount(context: Context, uri: Uri): Int {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val workbook = WorkbookFactory.create(inputStream)
                workbook.numberOfSheets // Trả về số sheet (bảng tính) trong tệp Excel
            } ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0 // Trả về 0 nếu có lỗi
        }
    }

    val getContent =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            selectedFileUri.value = uri
            uri?.let {
                fileAsByteArray.value = it.uriToByteArray(context)
                selectedFileName.value =
                    getFileName(context, it)?.replace(Regex("[^a-zA-Z0-9-.]"), "")
                val fileExtension = selectedFileName.value?.substringAfterLast(".") ?: "pdf"
                try {
                    if (fileExtension.equals("docx", ignoreCase = true)) {
                        numberPagesFile.value = getDocxPageCount(context, it)
                    }
                    else if (fileExtension.equals("pdf", ignoreCase = true)) {
                        numberPagesFile.value = getPdfPageCount(context, it)
                    }
                    else if (fileExtension.equals("xlsx", ignoreCase = true) || fileExtension.equals("xls", ignoreCase = true)) {
                        Log.e("Excel execution", "!!!")
                        numberPagesFile.value = getExcelSheetCount(context, it)
                        Log.e("ExcelPage: ", numberPagesFile.value.toString())
                    }

                }catch (e: Exception) {
                    Log.e("logPageCount", "Error: ${e.message}", e)
                }


                Log.d("buglo", numberPagesFile.value.toString())
            }


        }

    var fileCurrent by remember { mutableStateOf<FileDTO?>(null) }

    InfFileDialog(
        isOpen = isInfFileDialogOpen,
        file = fileCurrent ?: FileDTO(-1, " ", " ", " ", -1, -1),
        onDismissRequest = { isInfFileDialogOpen = false },
        onConfirmButtonClick = {
            fileViewModel.onEvent(FileEvent.DeleteFile(fileCurrent?.id ?: -1))
            isInfFileDialogOpen = false
        }
    )

    ConfirmDialog(
        isOpen = isConfirmDialogUpload,
        title = "Thông báo",
        bodyText = "Bạn có thật sự muốn tải lên không? ",
        onDismissRequest = { isConfirmDialogUpload = false },
        onConfirmButtonClick = {
            fileViewModel.onEvent(
                FileEvent.UploadFile(
                    selectedFileName.value ?: " ",
                    fileAsByteArray.value ?: byteArrayOf()
                )
            )
            fileViewModel.onEvent(
                FileEvent.SaveFile(
                    com.example.asmartprintingservice.domain.model.File(
                        selectedFileName.value ?: " ",
                        selectedFileName.value?.substringAfterLast(".") ?: " ",
                        LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")),
                        numberPagesFile.value ?: 0,
                    )
                )
            )
            isConfirmDialogUpload = false
        }
    )

    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(key1 = true) {
        fileViewModel.snackbarEventFlow.collectLatest {event ->
            when(event){
                SnackbarEvent.NavigateUp -> TODO()
                is SnackbarEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = event.duration
                    )
                }
            }
        }
    }

    Scaffold (
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    ){it -> it
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(2.dp, Color.Black, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedFileUri.value == null) {
                        Text(
                            text = "Bạn chưa chọn tệp",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A72B4),
                            modifier = Modifier.padding(vertical = 30.dp)
                        )
                    } else {
                        Text(
                            text = "Bạn đã chọn tệp: ${selectedFileName.value}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF3A72B4),
                            modifier = Modifier.padding(vertical = 30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            getContent.launch("*/*")
                        },
                        colors = ButtonDefaults.buttonColors(Blue),
                        shape = RoundedCornerShape(2.dp)
                    ) {
                        Text(
                            text = "Chọn tài liệu",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold,
                            color = Yellow
                        )
                    }

                    if (selectedFileUri.value != null) {
                        Button(
                            onClick = {
                                isConfirmDialogUpload = true
                            },
                            colors = ButtonDefaults.buttonColors(Blue),
                            shape = RoundedCornerShape(2.dp)
                        ) {
                            Text(
                                text = "Tải tài liệu",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                color = Yellow
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Tệp tin tải lên",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3A72B4)
                )

                Spacer(modifier = Modifier.height(20.dp))
            }

            if (fileState.isLoading) {
                item {
                    IndeterminateCircularIndicator()
                }
            } else {
                items(fileState.files ?: emptyList()) {
                    FrameUpload(
                        fileName = it.name,
                        fileType = it.type,
                        onButtonClick = {
                            fileViewModel.onEvent(FileEvent.onChangeCurrentFileId(it.id))
                            isConfirmDialogOpen = true
                        },
                        onClickItem = {
                            fileCurrent = it
                            isInfFileDialogOpen = true
                        }
                    )

                }
            }


        }
    }
}


