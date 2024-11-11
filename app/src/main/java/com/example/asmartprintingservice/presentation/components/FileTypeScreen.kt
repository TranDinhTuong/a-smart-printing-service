package com.example.asmartprintingservice.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.asmartprintingservice.R
import com.example.asmartprintingservice.util.FileType

@Composable
fun FileTypeScreen(type : String) : Painter{

    when(type){
        "pdf" -> {
            return painterResource(id = R.drawable.file_pdf)
        }
        "doc" -> {
            return painterResource(id = R.drawable.file_doc)
        }
        "docx" -> {
            return painterResource(id = R.drawable.file_doc)
        }

        "pptx" -> {
            return painterResource(id = R.drawable.file_pptx)
        }
    }
    return painterResource(id = R.drawable.file_pdf)
}