package com.example.asmartprintingservice.presentation.file

import androidx.compose.material3.SnackbarDuration
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.repository.FileRepository
import com.example.asmartprintingservice.util.SnackbarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _fileState = MutableStateFlow(FileState())
    val fileState : StateFlow<FileState>
        get() = _fileState

    fun onEvent(event: FileEvent) {
        when(event) {
            is FileEvent.LoadFiles -> {
                getFiles(userId = event.userId)
            }

            is FileEvent.SaveFile -> {
                saveFile(event.file)
            }

            is FileEvent.UploadFile -> {
                uploadFile(event.name, event.byteArray)
            }

            is FileEvent.DeleteFile -> {
                deleteFile(event.id)
            }

            is FileEvent.onChangeCurrentFileId -> {
                _fileState.value = FileState().copy(fileCurrentId = event.currentId)
            }
        }
    }

    private val _snackbarEventFlow = MutableSharedFlow<SnackbarEvent>()
    val snackbarEventFlow = _snackbarEventFlow.asSharedFlow()

    private fun deleteFile(id: Int) {
        viewModelScope.launch {
            fileRepository.deleteFile(id)
            _fileState.value = FileState().copy(isDelete = true)
        }
    }

    private fun getFiles(userId : String) {
        viewModelScope.launch {
            fileRepository.getFiles(userId).collect {
                when (it) {
                    is Resource.Error -> {
                        _fileState.value = FileState().copy(errorMsg = it.msg)
                    }
                    is Resource.Loading -> {
                        _fileState.value = FileState().copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _fileState.value = FileState().copy(files = it.data)
                    }
                }
            }
        }
    }

    private fun uploadFile(name : String, byteArray: ByteArray){
        viewModelScope.launch {
            _fileState.value = FileState().copy(url = fileRepository.uploadFile(name, byteArray))
            _fileState.value = FileState().copy(isUpload = true)
        }
    }

    private fun saveFile(file : File) {
        viewModelScope.launch {
            fileRepository.saveFile(file).collect{
                when(it){
                    is Resource.Error ->{
                        _fileState.value = FileState().copy(errorMsg = it.msg)

                        _snackbarEventFlow.emit(
                            SnackbarEvent.ShowSnackbar(
                                message = "Couldn't save file ${it.msg}",
                                duration = SnackbarDuration.Long
                            )
                        )
                    }
                    is Resource.Loading -> {
                        _fileState.value = FileState().copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        _fileState.value = FileState().copy(errorMsg = it.data)

                        _snackbarEventFlow.emit(
                            SnackbarEvent.ShowSnackbar(
                                message = "File saved successfully",
                                duration = SnackbarDuration.Long
                            )
                        )
                    }
                }
            }
        }
    }
}