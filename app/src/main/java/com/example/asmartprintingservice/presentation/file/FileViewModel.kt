package com.example.asmartprintingservice.presentation.file

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asmartprintingservice.core.Resource
import com.example.asmartprintingservice.domain.model.File
import com.example.asmartprintingservice.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
            FileEvent.LoadFiles -> {
                getFiles()
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

    private fun deleteFile(id: Int) {
        viewModelScope.launch {
            fileRepository.deleteFile(id)
            _fileState.value = FileState().copy(isDelete = true)
        }
    }

    private fun getFiles() {
        viewModelScope.launch {
            fileRepository.getFiles().collect {
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
            fileRepository.saveFile(file)
        }
    }
}