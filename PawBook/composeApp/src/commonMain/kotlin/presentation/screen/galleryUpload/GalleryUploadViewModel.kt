package presentation.screen.galleryUpload

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.isDirectory
import com.mohamedrejeb.calf.io.readByteArray
import core.util.Resources
import data.model.entity.PetPhotoEntity
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

class GalleryUploadViewModel(): ViewModel(), KoinComponent {
    private val galleryRepository: GalleryRepositoryImpl by inject()

    private val _state = MutableStateFlow(GalleryState.IDLE)
    val state: StateFlow<GalleryState> = _state

    fun uploadFiles(context: PlatformContext, files: List<KmpFile>, pets: ArrayList<PetPhotoEntity>, description: String) {
        viewModelScope.launch {

            val filesData: ArrayList<FileData> = arrayListOf()

            files.forEach {
                if (it.isDirectory(context)) {
                    _state.update { GalleryState.INVALID_FILE }
                    return@launch
                }

                val fileName = it.getName(context)
                val mediaType = fileName?.split(".")?.get(1).let { ext ->
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
                filesData.add(FileData(fileName as String, mediaType, file, pets.map { it.id }, description))
            }

            galleryRepository.uploadFiles(filesData).collect {
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