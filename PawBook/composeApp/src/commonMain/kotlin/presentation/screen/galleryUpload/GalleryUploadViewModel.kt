package presentation.screen.galleryUpload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.isDirectory
import com.mohamedrejeb.calf.io.readByteArray
import core.util.Resources
import data.repository.GalleryRepositoryImpl
import domain.model.enums.GalleryState
import domain.model.enums.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import presentation.screen.gallery.FileData
import utils.compose.ThumbnailGenerator

class GalleryUploadViewModel(): ViewModel(), KoinComponent {
    private val galleryRepository: GalleryRepositoryImpl by inject()
    private val thumbnailGenerator: ThumbnailGenerator by inject()

    private val _state = MutableStateFlow(GalleryState.IDLE)
    val state: StateFlow<GalleryState> = _state

    private val _selectedFiles = MutableStateFlow<ArrayList<FileData>>(arrayListOf())
    val selectedFiles: StateFlow<ArrayList<FileData>> = _selectedFiles

    private val _videoThumbnails = MutableStateFlow<MutableMap<FileData, ByteArray>>(mutableMapOf())
    val videoThumbnails: StateFlow<MutableMap<FileData, ByteArray>> = _videoThumbnails

    fun loadVideoPreviews(allFiles: ArrayList<FileData>) {
        viewModelScope.launch {
             val videoFiles = allFiles.filter { it.mediaType == MediaType.Video }
             thumbnailGenerator.generateThumbnails(videoFiles.map { it.data }).collect { list ->
                list.forEach {
                    try {
                        _videoThumbnails.value.put(videoFiles[list.indexOf(it)], it as ByteArray)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
             }
        }
    }

    fun loadFiles(context: PlatformContext, files: List<KmpFile>) {
        val newPets: ArrayList<FileData> = arrayListOf()
        viewModelScope.launch {
            _selectedFiles.value.clear()
            files.forEach {
                if (it.isDirectory(context)) {
                    _state.update { GalleryState.INVALID_FILE }
                    return@launch
                }

                val fileName = it.getName(context)
                val mediaType = fileName?.split(".")?.last().let { ext ->
                    when (ext?.uppercase() as String) {
                        "JPG", "JPEG", "PNG", "RAW" -> MediaType.Photo
                        "MP4", "MPEG", "MKV" -> MediaType.Video
                        else -> MediaType.Unknown
                    }
                }

                if (mediaType == MediaType.Unknown) {
                    _state.update { GalleryState.INVALID_FILE }
                    return@launch
                }

                val file = it.readByteArray(context)
                newPets.add(
                    FileData(
                        fileName as String,
                        mediaType,
                        file,
                        arrayListOf(),
                        ""
                    )
                )
            }
            _selectedFiles.value = newPets
            loadVideoPreviews(_selectedFiles.value)
        }
    }


    fun uploadFiles(files: ArrayList<FileData>) {
        viewModelScope.launch {
            galleryRepository.uploadFiles(files).collect {
                when (it) {
                    is Resources.Error -> {
                        if (it.message == "no_internet") _state.update { GalleryState.NO_INTERNET }
                        if (it.message == "internal_error") _state.update { GalleryState.ERROR }
                    }

                    is Resources.Loading -> {
                        _state.update { GalleryState.LOADING }
                    }

                    is Resources.Success -> {
                        _state.update { GalleryState.UPLOADED_FILE }
                    }
                }
            }
        }
    }
}