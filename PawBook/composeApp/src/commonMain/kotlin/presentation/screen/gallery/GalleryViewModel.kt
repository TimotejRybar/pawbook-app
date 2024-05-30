package presentation.screen.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.common.DatabaseSync
import core.util.Resources
import data.local.AppDatabase
import data.model.entity.PetPhotoEntity
import data.repository.GalleryRepositoryImpl
import data.repository.LoginRepositoryImpl
import domain.model.PetPhoto
import domain.model.enums.GalleryState
import domain.model.enums.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


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
