package presentation.screen.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.model.entity.PetPhotoEntity
import data.repository.GalleryRepositoryImpl
import domain.model.enums.GalleryState
import domain.model.enums.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@Serializable
data class FileData(
    val name: String,
    val mediaType: MediaType, val data: ByteArray,
    val pets: List<String>, val description: String)

class GalleryViewModel() : ViewModel(), KoinComponent {
    private val galleryRepository: GalleryRepositoryImpl by inject()

    private val _state = MutableStateFlow(GalleryState.IDLE)
    val state: StateFlow<GalleryState> = _state

    fun loadPhotos(onLoaded: (List<PetPhotoEntity>) -> Unit) {
        viewModelScope.launch {
            galleryRepository.fetch().collect {
                _state.update { GalleryState.SUCCESS }
                onLoaded(it)
            }
        }
    }
}
