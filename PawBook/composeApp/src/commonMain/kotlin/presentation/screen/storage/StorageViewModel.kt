package presentation.screen.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.isDirectory
import com.mohamedrejeb.calf.io.readByteArray
import core.util.Resources
import data.model.entity.StorageEntryEntity
import data.repository.StorageRepositoryImpl
import domain.model.enums.StorageEntryType
import domain.model.enums.StorageUploadState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class StorageViewModel(): ViewModel(), KoinComponent {
    private val storageRepository: StorageRepositoryImpl by inject()

    private val _allFiles = MutableStateFlow<ArrayList<StorageEntryEntity>>(arrayListOf())
    val allFiles: StateFlow<ArrayList<StorageEntryEntity>> = _allFiles

    private val _currentPath = MutableStateFlow(DEFAULT_DIRECTORY)
    val currentPath: StateFlow<String> = _currentPath

    private val _state = MutableStateFlow(StorageUploadState.IDLE)
    val state: StateFlow<StorageUploadState> = _state

    fun fetchStorage(currentPath: String, backString: String) {
        viewModelScope.launch {
            storageRepository.fetch(currentPath).collect { fetchedFiles ->
                val newFileList = arrayListOf<StorageEntryEntity>()
                if (currentPath != DEFAULT_DIRECTORY) {
                    newFileList.add(backStorageEntry(currentPath, backString))
                }
                newFileList.addAll(fetchedFiles)
                _allFiles.value = newFileList
                _currentPath.value = currentPath
            }
        }
    }

    private fun backStorageEntry(currentPath: String, backString: String): StorageEntryEntity {
        return StorageEntryEntity("...", StorageEntryType.RETURN,  backString, currentPath, "", LocalDateTime(
            LocalDate(1,1,1), LocalTime(1,1)),
            LocalDateTime(LocalDate(1,1,1), LocalTime(1,1))
        )
    }

    fun createFolder(name: String, currentPath: String) {
        viewModelScope.launch {
            storageRepository.createFolder(name, currentPath).collect {
                when(it){
                    is Resources.Error -> _state.update { StorageUploadState.ERROR }
                    is Resources.Loading -> _state.update { StorageUploadState.LOADING }
                    is Resources.Success -> {
                        _state.update { StorageUploadState.CREATED_FOLDER }
                    }
                }
            }
        }
    }

    fun uploadFile(context: PlatformContext, kmpFile: KmpFile, currentPath: String) {
        viewModelScope.launch {

            if(kmpFile.isDirectory(context))
                _state.update { StorageUploadState.INVALID_FILE }

            val fileName = kmpFile.getName(context)
            val storageEntryType = fileName?.split(".")?.get(1).let {
                when(it?.uppercase() as String){
                    "JPG","JPEG","PNG","RAW" -> StorageEntryType.PHOTO
                    "MP4","MPEG","MKV" -> StorageEntryType.VIDEO
                    "PDF" -> StorageEntryType.PDF
                    "DOC","DOCX" -> StorageEntryType.DOCUMENT
                    "ZIP","RAR" -> StorageEntryType.ARCHIVE
                    else -> StorageEntryType.UNKNOWN
                }
            }

            val file = kmpFile.readByteArray(context)
            storageRepository.uploadFile(fileName, file, storageEntryType, currentPath).collect {
                when (it) {
                    is Resources.Error -> {
                        if (it.message == "no_internet") _state.update { StorageUploadState.NO_INTERNET }
                        if (it.message == "internal_error") _state.update { StorageUploadState.ERROR }
                    }

                    is Resources.Loading -> {
                        _state.update { StorageUploadState.LOADING }
                    }

                    is Resources.Success -> {
                        _state.update { StorageUploadState.UPLOADED_FILE }
                    }
                }
            }
        }
    }

    fun setCurrentPath(path: String) {
        _currentPath.value = path
    }

    companion object {
        val DEFAULT_DIRECTORY = "/root/"
    }
}