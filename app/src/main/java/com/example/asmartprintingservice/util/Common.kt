package com.example.asmartprintingservice.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.material3.SnackbarDuration
import androidx.compose.ui.graphics.Color
import com.example.asmartprintingservice.data.model.HistoryDataDTO
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

enum class PaperTypeEnum(val title : String, val price : Int){
    A3(title = "A3", price = 250),
    A4(title = "A4", price = 300),
    A5(title = "A5", price = 400);
}

enum class FileType{
    PDF,
    DOC,
    DOCX,
    PPT,
    PPTX,
}

enum class Route(){
    welcome,
    slectRole,
    MainScreen,
    login,
    HomeScreen,
    Upload,
    Printing,
    Buying,
    History,
    ChatHelp,
    AdminMainScreen,
    AdminHomeScreen,
    ManagePrinter // thêm vào để test
}

fun Long?.changeMillisToDateString() : String{
    // this chính là giá trị biến gọi đến hàm
    val date : LocalDate = this?.let {
        Instant
            .ofEpochMilli(it)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    } ?: LocalDate.now() // null thi tra ve thoi gian hien tai
    return date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
}

@Throws(IOException::class)
fun Uri.uriToByteArray(context: Context) =
    context.contentResolver.openInputStream(this)?.use { it.buffered().readBytes() }

fun parseJsonData(jsonData: String): List<HistoryDataDTO> {
    return Json.decodeFromString(jsonData)
}

suspend fun uploadFile(context: Context, uri: Uri) : File {
    val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
    val file = java.io.File(context.cacheDir, "upload_file")
    val outputStream = FileOutputStream(file)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return file
}
fun convertDateString(input: String): String {
    val inputFormat = SimpleDateFormat("dd 'thg' MM yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val date = inputFormat.parse(input)
    return outputFormat.format(date)
}

fun getFileName(context: Context, uri: Uri): String? {
    var name: String? = null
    val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
    cursor?.use {
        if (it.moveToFirst()) {
            name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME) ?: -1)
        }
    }
    return name
}

sealed class SnackbarEvent{

    data class ShowSnackbar(
        val message : String,
        val duration : SnackbarDuration = SnackbarDuration.Short
    ) : SnackbarEvent()

    data object NavigateUp : SnackbarEvent()
}